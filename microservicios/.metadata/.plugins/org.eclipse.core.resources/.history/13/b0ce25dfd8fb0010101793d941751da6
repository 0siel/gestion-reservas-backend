package msv_huespedes.mappers;

import org.springframework.stereotype.Component;

import com.example.demo.dto.HuespedRequest;
import com.example.demo.dto.HuespedResponse;
import com.example.demo.mappers.CommonMapper;

import msv_huespedes.entities.Huesped;

@Component
public class HuespedMapper implements CommonMapper<HuespedRequest, HuespedResponse, Huesped> {

    @Override
    public HuespedResponse entityToResponse(Huesped entity) {
        if (entity == null) return null;
        String nombreCompleto = String.join(" ", entity.getNombre(), entity.getApellido());
        return new HuespedResponse(
                entity.getId(),
                nombreCompleto,
                entity.getEmail(),
                entity.getDocumento(),
                entity.getNacionalidad()
        );
    }

    @Override
    public Huesped requestToEntity(HuespedRequest request) {
        if (request == null) return null;
        Huesped huesped = new Huesped();
        huesped.setNombre(request.nombre());
        huesped.setApellido(request.apellido());
        huesped.setEmail(request.email());
        huesped.setDocumento(request.documento());
        huesped.setNacionalidad(request.nacionalidad());
        return huesped;
    }

    @Override
    public Huesped updateEntityFromRequest(HuespedRequest request, Huesped entity) {
        if (request == null || entity == null) return entity;
        entity.setNombre(request.nombre());
        entity.setApellido(request.apellido());
        entity.setEmail(request.email());
        entity.setDocumento(request.documento());
        entity.setNacionalidad(request.nacionalidad());
        return entity;
    }
}
