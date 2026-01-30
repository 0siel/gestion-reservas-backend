package msv_reservas.services;

import com.example.demo.dto.ReservaRequest;
import com.example.demo.dto.ReservaResponse;
import com.example.demo.services.CrudService;

public interface ReservaService extends CrudService<ReservaRequest, ReservaResponse> {

	public ReservaResponse actualizarStatus(Long idReserva, Long idStatusReserva);
	
	boolean tieneReservasActivasHuesped(Long idHuesped);
	boolean tieneReservasActivasHabitacion(Long idHabitacion);
	
	ReservaResponse realizarCheckIn(Long idReserva);
    ReservaResponse realizarCheckOut(Long idReserva);
	
}
