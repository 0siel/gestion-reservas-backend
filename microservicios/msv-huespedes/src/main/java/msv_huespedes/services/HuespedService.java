package msv_huespedes.services;

import com.example.demo.dto.HuespedRequest;
import com.example.demo.dto.HuespedResponse;
import com.example.demo.services.CrudService;

public interface HuespedService extends CrudService<HuespedRequest, HuespedResponse> {
    
    // Este trae el dato aunque esté ELIMINADO (Para reportes o historial)
    HuespedResponse obtenerPorIdSinEstado(Long id);
}
