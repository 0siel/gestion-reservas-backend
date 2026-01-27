package msv_habitaciones.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controllers.CommonController;
import com.example.demo.dto.HabitacionRequest;
import com.example.demo.dto.HabitacionResponse;

import msv_habitaciones.services.HabitacionService;

@RestController
public class HabitacionController extends CommonController<HabitacionRequest, HabitacionResponse, HabitacionService> {

    public HabitacionController(HabitacionService service) {
        super(service);
    }

    @GetMapping("/id-habitacion/{id}")
    public ResponseEntity<HabitacionResponse> obtenerHabitacionSinEstado(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerHabitacionPorIdSinEstado(id));
    }
}
