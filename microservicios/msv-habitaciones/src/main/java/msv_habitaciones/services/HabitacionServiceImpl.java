package msv_habitaciones.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.clients.ReservaClient;
import com.example.demo.dto.HabitacionRequest;
import com.example.demo.dto.HabitacionResponse;
import com.example.demo.enums.EstadoHabitacion;
import com.example.demo.enums.EstadoRegistro;
import com.example.demo.exceptions.RecursoDuplicadoException;
import com.example.demo.exceptions.RecursoEnUsoException;
import com.example.demo.exceptions.RecursoNoEncontradoException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import msv_habitaciones.entities.Habitacion;
import msv_habitaciones.mappers.HabitacionMapper;
import msv_habitaciones.repositories.HabitacionRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class HabitacionServiceImpl implements HabitacionService {

    private final HabitacionRepository repository;
    private final HabitacionMapper mapper;

    private final ReservaClient reservaClient;

    // --- MÉTODOS HEREDADOS DE CRUDSERVICE ---

    @Override
    @Transactional(readOnly = true)
    public List<HabitacionResponse> listar() {
        // Sobrescribimos para filtrar solo ACTIVO
        return repository.findAllByEstadoRegistro(EstadoRegistro.ACTIVO).stream()
                .map(mapper::entityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public HabitacionResponse obtenerPorId(Long id) {
        // Sobrescribimos para validar que sea ACTIVO
        return mapper.entityToResponse(getOrThrow(id));
    }

    @Override
    @Transactional
    public HabitacionResponse registrar(HabitacionRequest request) {
        log.info("Registrando habitación: {}", request.numero());
        
        validarUnicidad(request.numero(), null);

        Habitacion habitacion = mapper.requestToEntity(request);
        habitacion.setEstadoRegistro(EstadoRegistro.ACTIVO); // Estado inicial forzoso

        return mapper.entityToResponse(repository.save(habitacion));
    }

    @Override
    @Transactional
    public HabitacionResponse actualizar(HabitacionRequest request, Long id) {
        Habitacion habitacion = getOrThrow(id);
        
        // Si cambia el número, validamos colisión
        if (!habitacion.getNumero().equalsIgnoreCase(request.numero())) {
            validarUnicidad(request.numero(), id);
        }

        Habitacion actualizada = mapper.updateEntityFromRequest(request, habitacion);
        return mapper.entityToResponse(repository.save(actualizada));
    }

 

    @Override
    @Transactional
    public void eliminar(Long id) {
        Habitacion habitacion = getOrThrow(id);
        
        // 2. VALIDACIÓN: ¿Está reservada?
        boolean tieneReservas = reservaClient.tieneReservasHabitacion(id);
        
        if (tieneReservas) {
            throw new RecursoEnUsoException("No se puede eliminar la habitación porque tiene reservas CONFIRMADAS o EN CURSO pendientes.");
        }
        
        log.warn("Eliminando lógicamente habitación: {}", id);
        habitacion.setEstadoRegistro(EstadoRegistro.ELIMINADO);
        repository.save(habitacion);
    }

    // --- MÉTODO ESPECÍFICO (NO ESTÁ EN CRUDSERVICE) ---

    @Override
    @Transactional(readOnly = true)
    public HabitacionResponse obtenerPorIdSinEstado(Long id) {
        // Busca físico directo (incluye eliminados)
        Habitacion habitacion = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Habitación no encontrada en histórico con ID: " + id));
        return mapper.entityToResponse(habitacion);
    }
    
    @Override
    @Transactional
    public void cambiarEstado(Long id, String nuevoEstado) {
        Habitacion habitacion = getOrThrow(id); // Usa tu método privado existente
        
        try {
            // Asegúrate de importar tu Enum EstadoHabitacion
            // Convertimos el String "OCUPADA" al Enum
            EstadoHabitacion estadoEnum = EstadoHabitacion.valueOf(nuevoEstado.toUpperCase());
            
            habitacion.setEstado(estadoEnum);
            repository.save(habitacion);
            
            log.info("Estado de habitación {} cambiado a {}", id, estadoEnum);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("El estado '" + nuevoEstado + "' no es válido.");
        }
    }
    // --- HELPERS ---

    private Habitacion getOrThrow(Long id) {
        return repository.findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO)
                .orElseThrow(() -> new RecursoNoEncontradoException("Habitación no encontrada o inactiva con ID: " + id));
    }

    private void validarUnicidad(String numero, Long id) {
        boolean existe;
        if (id == null) {
            existe = repository.existsByNumeroAndEstadoRegistro(numero, EstadoRegistro.ACTIVO);
        } else {
            existe = repository.existsByNumeroAndIdNotAndEstadoRegistro(numero, id, EstadoRegistro.ACTIVO);
        }

        if (existe) {
            throw new RecursoDuplicadoException("El número de habitación '" + numero + "' ya está ocupado.");
        }
    }
}