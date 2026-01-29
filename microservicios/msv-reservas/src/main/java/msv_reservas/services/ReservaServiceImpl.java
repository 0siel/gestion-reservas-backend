package msv_reservas.services;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.HabitacionResponse;
import com.example.demo.dto.ReservaRequest;
import com.example.demo.dto.ReservaResponse;
import com.example.demo.enums.EstadoRegistro;
import com.example.demo.enums.EstadoReserva;

import lombok.RequiredArgsConstructor;
import msv_reservas.entities.Reserva;
import msv_reservas.mappers.ReservaMapper;
import msv_reservas.repositories.ReservaRepository;
import com.example.demo.clients.HabitacionClient;


@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {
	

	   private final ReservaRepository reservaRepository;
	   private final ReservaMapper mapper;
	   private final HabitacionClient habitacionClient;
	 
	   
	   
	
	
	@Override
	@Transactional(readOnly = true)
	public List<ReservaResponse> listar() {
					 return reservaRepository.findAll().stream()
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
		Reserva reserva = mapper.requestToEntity(request);
		
		//Llamado a los métodos auxiliares 
		reserva.setCantNoches(calcularNoches(reserva));
		reserva.setMontoTotal(montoTotal(reserva));
		
		//Llamado al estado registro por defecto 
		//reserva.setEstadoRegistro(EstadoRegistro.ACTIVO);

		
	    return mapper.entityToResponse(reservaRepository.save(reserva));
		
	}

	@Override
	@Transactional
	public ReservaResponse actualizar(ReservaRequest request, Long id) {
		Reserva reservaExistente = getEntityOrThrow(id);
        Reserva reservaActualizado = mapper.updateEntityFromRequest(request, reservaExistente);
        
        //Recalcular noche si cambian fechas  
        reservaActualizado.setCantNoches(calcularNoches(reservaActualizado));
        //Recalcular monto total 
        reservaActualizado.setMontoTotal(montoTotal(reservaActualizado));

        
        return mapper.entityToResponse(reservaRepository.save(reservaActualizado));
		
	
	}

	@Override
	@Transactional
	public void eliminar(Long id) {
		Reserva reserva = getEntityOrThrow(id);
        reservaRepository.delete(reserva);
		
	}
	
	private Reserva getEntityOrThrow(Long id) {
        return reservaRepository.findByIdAndEstado(id, EstadoRegistro.ACTIVO) 
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con ID: " + id));

}
	
	//Método auxiliar para calcular el total de noches entre 2 fechas 
	 private int calcularNoches(Reserva reserva) {
	        if(reserva.getFechaEntrada() != null && reserva.getFechaSalida() != null) {
	            return (int) ChronoUnit.DAYS.between(reserva.getFechaEntrada(), reserva.getFechaSalida());
	        }
	        return 0;
	 }
	 
	 
	 
	 //Método auxiliar para calcular el monto total * cantidad de noches  
	 private BigDecimal montoTotal (Reserva reserva) {
		 if (reserva.getCantNoches() <= 0) {
		        throw new RuntimeException("La cantidad de noches debe ser mayor a 0");
		    }

		 // Traer habitación de su microservicio usando feig client 
		    HabitacionResponse habitacion = habitacionClient.obtenerHabitacionPorId(reserva.getIdHabitacion());

		    // Calcular monto total
		    return habitacion.precioNoche().multiply(BigDecimal.valueOf(reserva.getCantNoches()));
		 
	 }
	   //Validar la transición de los estados de la reserva 
	 private void validarTransicion (EstadoReserva actual, EstadoReserva nuevo) {
		 boolean valida = switch (actual) {
		 case CONFIRMADA -> (nuevo == EstadoReserva.EN_CURSO || nuevo == EstadoReserva.CANCELADA);
		 case EN_CURSO -> (nuevo == EstadoReserva.FINALIZADA);
		 case FINALIZADA, CANCELADA -> false;
		 };
		 if (!valida) {
			 throw new IllegalStateException("Transición no válida de "+ actual + " a " + nuevo );
		 }
	 }
	 
	 public ReservaResponse actualizarStatus(Long idReserva, Long idStatusReserva) {
		 EstadoReserva nuevoEstado = EstadoReserva.fromCodigo(idStatusReserva);
		 Reserva reservaActual = getEntityOrThrow(idStatusReserva);
		 validarTransicion (reservaActual.getEstado(),nuevoEstado);
		 reservaActual.setEstado(nuevoEstado);
		Reserva reservaActualizada = reservaRepository.save(reservaActual);
		return mapper.entityToResponse(reservaActualizada);
		 
		
	 }
	 
}
			 
	
	 
		 



