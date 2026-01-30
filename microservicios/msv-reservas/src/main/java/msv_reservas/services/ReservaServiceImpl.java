package msv_reservas.services;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.clients.HabitacionClient;
import com.example.demo.clients.HuespedClient;
import com.example.demo.dto.HabitacionResponse;
import com.example.demo.dto.ReservaRequest;
import com.example.demo.dto.ReservaResponse;
import com.example.demo.enums.EstadoRegistro;
import com.example.demo.enums.EstadoReserva;
import com.example.demo.exceptions.HabitacionNoDisponibleException;
import com.example.demo.exceptions.RecursoNoEncontradoException;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import msv_reservas.entities.Reserva;
import msv_reservas.mappers.ReservaMapper;
import msv_reservas.repositories.ReservaRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final ReservaMapper mapper;
    private final HabitacionClient habitacionClient;
    private final HuespedClient huespedClient;

    private static final String HABITACION_DISPONIBLE = "DISPONIBLE";
    private static final String HABITACION_OCUPADA = "OCUPADA";

    @Override
    @Transactional(readOnly = true)
    public List<ReservaResponse> listar() {
        return reservaRepository.findAllByEstadoRegistro(EstadoRegistro.ACTIVO).stream()
                .map(mapper::entityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ReservaResponse obtenerPorId(Long id) {
        return mapper.entityToResponse(getEntityOrThrow(id));
    }

    @Override
    @Transactional
    public ReservaResponse registrar(ReservaRequest request) {
        log.info("Iniciando registro de reserva...");

        validarFechas(request);
        validarHuesped(request.idHuesped());

        // 1. Validar Disponibilidad
        HabitacionResponse habitacion = validarHabitacionDisponible(request.idHabitacion());

        Reserva reserva = mapper.requestToEntity(request);
        reserva.setCantNoches(calcularNoches(reserva));
        reserva.setMontoTotal(habitacion.precioNoche().multiply(BigDecimal.valueOf(reserva.getCantNoches())));
        
        reserva.setEstado(EstadoReserva.CONFIRMADA);
        reserva.setEstadoRegistro(EstadoRegistro.ACTIVO);

        Reserva guardada = reservaRepository.save(reserva);

        // 2. Marcar Habitación como OCUPADA
        try {
            habitacionClient.cambiarEstadoHabitacion(request.idHabitacion(), HABITACION_OCUPADA);
            log.info("Habitación {} marcada como OCUPADA", request.idHabitacion());
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar estado de la habitación. Rollback ejecutado.");
        }

        return mapper.entityToResponse(guardada);
    }

    @Override
    @Transactional
    public ReservaResponse actualizar(ReservaRequest request, Long id) {
        Reserva reservaExistente = getEntityOrThrow(id);

        if (reservaExistente.getEstado() == EstadoReserva.FINALIZADA || 
            reservaExistente.getEstado() == EstadoReserva.CANCELADA) {
            throw new IllegalStateException("No se pueden modificar reservas finalizadas o canceladas.");
        }

        // --- VALIDACIÓN EXTRA ---
        // Si intentan cambiar el ID de la habitación en el update, lanzamos error.
        // Cambiar de habitación es complejo (liberar A, ocupar B), mejor bloquearlo aquí.
        if (!reservaExistente.getIdHabitacion().equals(request.idHabitacion())) {
            throw new IllegalArgumentException("No se puede cambiar la habitación en una edición. Cancele y cree una nueva reserva.");
        }

        validarFechas(request);

        // Actualizamos (Asumiendo que solo cambian fechas)
        Reserva reservaActualizada = mapper.updateEntityFromRequest(request, reservaExistente);
        reservaActualizada.setCantNoches(calcularNoches(reservaActualizada));
        
        // Recalculamos monto
        HabitacionResponse habitacion = habitacionClient.obtenerHabitacionActiva(reservaActualizada.getIdHabitacion());
        reservaActualizada.setMontoTotal(habitacion.precioNoche().multiply(BigDecimal.valueOf(reservaActualizada.getCantNoches())));

        return mapper.entityToResponse(reservaRepository.save(reservaActualizada));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Reserva reserva = getEntityOrThrow(id);

        // --- VALIDACIÓN NUEVA ---
        // Solo permitir eliminar si está CONFIRMADA.
        // Si ya hicieron Check-in (EN_CURSO) o ya terminó (FINALIZADA), no se toca.
        if (!EstadoReserva.CONFIRMADA.equals(reserva.getEstado())) {
            throw new IllegalStateException(
                "Solo se pueden eliminar reservas que estén en estado CONFIRMADA. " +
                "El estado actual es: " + reserva.getEstado()
            );
        }

        log.warn("Eliminando lógicamente la reserva ID: {}", id);
        
        // Como validamos que está CONFIRMADA, sabemos que la habitación estaba apartada (OCUPADA).
        // Por lo tanto, es OBLIGATORIO liberarla al eliminar la reserva.
        liberarHabitacion(reserva.getIdHabitacion());

        // Borrado Lógico
        reserva.setEstadoRegistro(EstadoRegistro.ELIMINADO);
        
        // Para consistencia de negocio, también la marcamos como CANCELADA
        // así si alguien consulta el histórico de borrados, sabe que no procedió.
        reserva.setEstado(EstadoReserva.CANCELADA); 
        
        reservaRepository.save(reserva);
    }

    @Override
    @Transactional
    public ReservaResponse actualizarStatus(Long idReserva, Long idStatusReserva) {
        Reserva reservaActual = getEntityOrThrow(idReserva);
        EstadoReserva nuevoEstado = EstadoReserva.fromCodigo(idStatusReserva);
        
        validarTransicion(reservaActual.getEstado(), nuevoEstado);
        
        // --- LOGICA AGREGADA: LIBERAR HABITACIÓN ---
        // Si hacen Check-out (FINALIZADA) o Cancelan, liberamos la habitación
        if (nuevoEstado == EstadoReserva.FINALIZADA || nuevoEstado == EstadoReserva.CANCELADA) {
            liberarHabitacion(reservaActual.getIdHabitacion());
        }

        reservaActual.setEstado(nuevoEstado);
        return mapper.entityToResponse(reservaRepository.save(reservaActual));
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean tieneReservasActivasHuesped(Long idHuesped) {
    	log.info("Validando usuario para eliminar");
        List<EstadoReserva> estadosActivos = List.of(EstadoReserva.CONFIRMADA, EstadoReserva.EN_CURSO);
        return reservaRepository.existsByIdHuespedAndEstadoInAndEstadoRegistro(idHuesped, estadosActivos, EstadoRegistro.ACTIVO);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean tieneReservasActivasHabitacion(Long idHabitacion) {
        List<EstadoReserva> estadosActivos = List.of(EstadoReserva.CONFIRMADA, EstadoReserva.EN_CURSO);
        return reservaRepository.existsByIdHabitacionAndEstadoInAndEstadoRegistro(idHabitacion, estadosActivos, EstadoRegistro.ACTIVO);
    }
    
    @Override
    @Transactional
    public ReservaResponse realizarCheckIn(Long idReserva) {
        Reserva reserva = getEntityOrThrow(idReserva);
        
        log.info("Procesando Check-in para reserva ID: {}", idReserva);

        // REGLA: Solo se puede hacer Check-in si está CONFIRMADA
        if (!EstadoReserva.CONFIRMADA.equals(reserva.getEstado())) {
            throw new IllegalStateException("El Check-in solo se puede realizar sobre reservas CONFIRMADAS. Estado actual: " + reserva.getEstado());
        }

        // Cambio de estado
        reserva.setEstado(EstadoReserva.EN_CURSO);
        
        // Opcional: Podrías validar aquí si la fecha actual coincide con fechaEntrada
        // if (!LocalDate.now().equals(reserva.getFechaEntrada())) { ... }

        return mapper.entityToResponse(reservaRepository.save(reserva));
    }

    @Override
    @Transactional
    public ReservaResponse realizarCheckOut(Long idReserva) {
        Reserva reserva = getEntityOrThrow(idReserva);
        
        log.info("Procesando Check-out para reserva ID: {}", idReserva);

        // REGLA: "No se puede hacer check-out antes de hacer check-in"
        // Esto se cumple validando que el estado actual sea EN_CURSO
        if (!EstadoReserva.EN_CURSO.equals(reserva.getEstado())) {
            throw new IllegalStateException("El Check-out solo aplica a reservas EN CURSO (Check-in ya realizado). Estado actual: " + reserva.getEstado());
        }

        // 1. Cambiar estado de la reserva
        reserva.setEstado(EstadoReserva.FINALIZADA);
        
        // 2. REGLA: La habitación cambia a DISPONIBLE
        try {
            habitacionClient.cambiarEstadoHabitacion(reserva.getIdHabitacion(), HABITACION_DISPONIBLE);
            log.info("Habitación {} liberada por Check-out", reserva.getIdHabitacion());
        } catch (Exception e) {
            // Si falla la comunicación con Habitaciones, hacemos rollback para no dejar inconsistencia
            throw new RuntimeException("Error al liberar la habitación durante el Check-out. Intente nuevamente.");
        }

        return mapper.entityToResponse(reservaRepository.save(reserva));
    }

    // --- MÉTODOS DE APOYO ---

    private void liberarHabitacion(Long idHabitacion) {
        try {
            habitacionClient.cambiarEstadoHabitacion(idHabitacion, HABITACION_DISPONIBLE);
            log.info("Habitación {} liberada (DISPONIBLE)", idHabitacion);
        } catch (Exception e) {
            // Logueamos error pero no detenemos el proceso (Fail-safe), 
            // o lanzamos excepción si es crítico para tu negocio.
            log.error("Error al liberar la habitación {}", idHabitacion, e);
        }
    }

    private Reserva getEntityOrThrow(Long id) {
        return reservaRepository.findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO)
                .orElseThrow(() -> new RecursoNoEncontradoException("Reserva no encontrada con ID: " + id));
    }

    private void validarTransicion(EstadoReserva actual, EstadoReserva nuevo) {
         boolean valida = switch (actual) {
            case CONFIRMADA -> (nuevo == EstadoReserva.EN_CURSO || nuevo == EstadoReserva.CANCELADA);
            case EN_CURSO -> (nuevo == EstadoReserva.FINALIZADA);
            case FINALIZADA, CANCELADA -> false;
        };
        if (!valida) throw new IllegalStateException("Transición inválida de " + actual + " a " + nuevo);
    }
    
    private void validarFechas(ReservaRequest request) {
        if (!request.fechaSalida().isAfter(request.fechaEntrada())) {
            throw new IllegalArgumentException("La fecha de salida debe ser posterior a la fecha de entrada.");
        }
    }

    private HabitacionResponse validarHabitacionDisponible(Long idHabitacion) {
        try {
            // 1. Intentamos obtener la habitación (Validar Existencia)
            HabitacionResponse habitacion = habitacionClient.obtenerHabitacionActiva(idHabitacion);
            
            // 2. Validamos el estado (Validar Disponibilidad)
            if (!HABITACION_DISPONIBLE.equalsIgnoreCase(habitacion.estado())) {
                // CAMBIO: Lanzamos nuestra excepción personalizada
                throw new HabitacionNoDisponibleException(
                    "La habitación " + habitacion.numero() + " no se puede reservar porque su estado es: " + habitacion.estado()
                );
            }
            return habitacion;

        } catch (FeignException.NotFound e) {
            // 3. Capturamos el 404 del Feign Client si no existe
            throw new RecursoNoEncontradoException("No se encontró la habitación con ID " + idHabitacion + " en el sistema.");
        }
    }

    private void validarHuesped(Long idHuesped) {
        try {
            huespedClient.obtenerHuespedActivo(idHuesped);
        } catch (FeignException.NotFound e) {
            throw new RecursoNoEncontradoException("El huésped ID " + idHuesped + " no existe.");
        }
    }

    private int calcularNoches(Reserva reserva) {
        return (int) ChronoUnit.DAYS.between(reserva.getFechaEntrada(), reserva.getFechaSalida());
    }
}