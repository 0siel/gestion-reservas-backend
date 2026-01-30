package msv_reservas.controllers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controllers.CommonController;
import com.example.demo.dto.ReservaRequest;
import com.example.demo.dto.ReservaResponse;

import msv_reservas.services.ReservaService;




@RestController
//@RequiredArgsConstructor
public class ReservaController extends CommonController<ReservaRequest, ReservaResponse, ReservaService>{

	public ReservaController(ReservaService service) {
		super(service);
		
	}

	
 
 
 @PutMapping("/{id}/estado/{idStatusReserva}")
 public  ResponseEntity<ReservaResponse> actualizarStatus(
         @PathVariable Long id,
         @PathVariable Long idStatusReserva) {

    return ResponseEntity.ok(service.actualizarStatus(id, idStatusReserva));
 }
 
 @GetMapping("/check-huesped/{id}")
 public ResponseEntity<Boolean> verificarReservasHuesped(@PathVariable Long id) {
     return ResponseEntity.ok(service.tieneReservasActivasHuesped(id));
 }

 @GetMapping("/check-habitacion/{id}")
 public ResponseEntity<Boolean> verificarReservasHabitacion(@PathVariable Long id) {
     return ResponseEntity.ok(service.tieneReservasActivasHabitacion(id));
 }
 
 @PutMapping("/{id}/check-in")
 public ResponseEntity<ReservaResponse> checkIn(@PathVariable Long id) {
     return ResponseEntity.ok(service.realizarCheckIn(id));
 }

 
 @PutMapping("/{id}/check-out")
 public ResponseEntity<ReservaResponse> checkOut(@PathVariable Long id) {
     return ResponseEntity.ok(service.realizarCheckOut(id));
 }

}
