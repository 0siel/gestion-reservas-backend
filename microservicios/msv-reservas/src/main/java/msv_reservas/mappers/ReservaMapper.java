package msv_reservas.mappers;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.demo.dto.ReservaRequest;
import com.example.demo.dto.ReservaResponse;
import com.example.demo.enums.EstadoReserva;
import com.example.demo.mappers.CommonMapper;
import msv_reservas.entities.Reserva;

@Component
public class ReservaMapper implements CommonMapper<ReservaRequest, ReservaResponse, Reserva>{

	@Override
	public ReservaResponse entityToResponse(Reserva entity) {
		if(entity == null) return null;
		return new ReservaResponse(
		entity.getId(),
		entity.getIdHuesped(),
		entity.getIdHabitacion(),
		entity.getFechaEntrada(),
		entity.getFechaSalida(),
		entity.getCantNoches(),
		entity.getMontoTotal(),
		entity.getEstado() != null ? entity.getEstado().name() : null, //Conversión de enum a string
		entity.getFechaCreacion()
		);
		
	}

	@Override
	public Reserva requestToEntity(ReservaRequest request) {
			if(request == null) return null;
			Reserva reserva = new Reserva();
			reserva.setIdHuesped(request.idHuesped());
			reserva.setIdHabitacion(request.idHabitacion());
			reserva.setFechaEntrada(request.fechaEntrada());
			reserva.setFechaSalida(request.fechaSalida());
			reserva.setEstado(EstadoReserva.CONFIRMADA);
			reserva.setFechaCreacion(LocalDateTime.now());
			
			return reserva;
	}

	@Override
	public Reserva updateEntityFromRequest(ReservaRequest request, Reserva entity) {
	    if(request == null || entity == null) return entity; 
	    entity.setIdHuesped(request.idHuesped());
	    entity.setIdHabitacion(request.idHabitacion());
	    entity.setFechaEntrada(request.fechaEntrada());
	    entity.setFechaSalida(request.fechaSalida());
	  //  entity.setCantNoches(request.cantNoches());
	    //entity.setMontoTotal(request.montoTotal());
	    //entity.setEstado(request.estado());
	    //entity.setFechaCreacion(request.fechaCreacion());
	    return entity;
	}
}

