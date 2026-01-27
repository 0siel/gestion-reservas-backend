package msv_habitaciones.services;

import com.example.demo.dto.HabitacionRequest;
import com.example.demo.dto.HabitacionResponse;
import com.example.demo.services.CrudService;

public interface HabitacionService extends CrudService<HabitacionRequest, HabitacionResponse> {
    
    HabitacionResponse obtenerHabitacionPorIdSinEstado(Long id);
}
