package msv_reservas.controllers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controllers.CommonController;
import com.example.demo.dto.ReservaRequest;
import com.example.demo.dto.ReservaResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import msv_reservas.services.ReservaService;




@RestController
//@RequiredArgsConstructor
public class ReservaController extends CommonController<ReservaRequest, ReservaResponse, ReservaService>{

	public ReservaController(ReservaService service) {
		super(service);
		
	}

	
 
 //EndPonint para el estado de la reserva 
 @PutMapping("/{id}/estado/{idStatusReserva}")
 public  ResponseEntity<ReservaResponse> actualizarStatus(
         @PathVariable Long id,
         @PathVariable Long idStatusReserva) {

    return ResponseEntity.ok(service.actualizarStatus(id, idStatusReserva));
 }

 
 


}
