package msv_reservas.mappers;

import org.springframework.stereotype.Component;

import com.example.demo.dto.ReservaRequest;
import com.example.demo.dto.ReservaResponse;
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
		entity.getEstado(),
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
	}

	@Override
	public Reserva updateEntityFromRequest(ReservaRequest Request, Reserva entity) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	
}
