package msv_habitaciones.services;


import com.example.demo.dto.HabitacionRequest;
import com.example.demo.dto.HabitacionResponse;
import com.example.demo.services.CrudService; // Tu interfaz común

public interface HabitacionService extends CrudService<HabitacionRequest, HabitacionResponse> {
    
    // Método EXTRA requerido por el microservicio de Reservas
    // (No está en CrudService porque es específico de este negocio)
    HabitacionResponse obtenerPorIdSinEstado(Long id);
    
 // En Service Interface
    void cambiarEstado(Long id, String nuevoEstado);
}
