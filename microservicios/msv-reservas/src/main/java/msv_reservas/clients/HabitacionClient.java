package msv_reservas.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.demo.dto.HabitacionResponse; // Asegúrate que este DTO exista en tu commons

@FeignClient(name = "msv-habitaciones") // El nombre debe coincidir con el spring.application.name del servicio de habitaciones
public interface HabitacionClient {
    
    @GetMapping("/habitaciones/{id}")
    HabitacionResponse obtenerPorId(@PathVariable("id") Long id);
}