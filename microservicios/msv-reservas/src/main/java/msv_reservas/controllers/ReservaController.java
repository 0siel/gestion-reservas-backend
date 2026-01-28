package msv_reservas.controllers;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controllers.CommonController;
import com.example.demo.dto.ReservaRequest;
import com.example.demo.dto.ReservaResponse;

import msv_reservas.services.ReservaService;


@RestController

public class ReservaController extends CommonController<ReservaRequest, ReservaResponse, ReservaService>{

	public ReservaController(ReservaService service) {
		super(service);
		// TODO Auto-generated constructor stub
	}
	
	//boolean HuespedTieneReservasActivas
	//boolean HabitacionesTieneReservasActivas
	

	



}
