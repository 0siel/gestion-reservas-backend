package msv_habitaciones.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.controllers.CommonController;
import com.example.demo.dto.HabitacionRequest;
import com.example.demo.dto.HabitacionResponse;
import msv_habitaciones.services.HabitacionService;

@RestController

public class HabitacionController extends CommonController<HabitacionRequest, HabitacionResponse, HabitacionService> {

    public HabitacionController(HabitacionService service) {
        super(service);
    }

    
    @GetMapping("/historico/{id}")
    public ResponseEntity<HabitacionResponse> obtenerHistorico(@PathVariable Long id) {
        
        return ResponseEntity.ok(service.obtenerPorIdSinEstado(id));
    }
    
    @PutMapping("/{id}/cambiar-estado")
    public ResponseEntity<Void> cambiarEstado(@PathVariable Long id, @RequestParam String estado) {
        service.cambiarEstado(id, estado);
        return ResponseEntity.ok().build();
    }
}
