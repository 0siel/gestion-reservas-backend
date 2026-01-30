package msv_huespedes.mappers;

import org.springframework.stereotype.Component;

import com.example.demo.dto.HuespedRequest;
import com.example.demo.dto.HuespedResponse;
import com.example.demo.enums.EstadoRegistro;
import com.example.demo.mappers.CommonMapper;

import msv_huespedes.entities.Huesped;


@Component
public class HuespedMapper implements CommonMapper<HuespedRequest, HuespedResponse, Huesped> {

    @Override
    public HuespedResponse entityToResponse(Huesped entity) {
        if (entity == null) return null;
        return new HuespedResponse(
                entity.getId(),
                entity.getNombre(),
                entity.getApellidoPaterno(),
                entity.getApellidoMaterno(),
                entity.getEmail(),
                entity.getNacionalidad().getNombre(),
                entity.getTelefono(),
                entity.getDocumento()// Enum EstadoRegistro
        );
    }

    @Override
    public Huesped requestToEntity(HuespedRequest request) {
        if (request == null) return null;
        Huesped huesped = new Huesped();
        huesped.setNombre(request.nombre());
        huesped.setApellidoPaterno(request.apellidoPaterno());
        huesped.setApellidoMaterno(request.apellidoMaterno());
        huesped.setEmail(request.email());
        huesped.setTelefono(request.telefono());
        huesped.setDocumento(request.documento());
        huesped.setNacionalidad(request.nacionalidad());
        huesped.setEstadoRegistro(EstadoRegistro.ACTIVO); // Estado por defecto
        return huesped;
    }

    @Override
    public Huesped updateEntityFromRequest(HuespedRequest request, Huesped entity) {
        if (request == null || entity == null) return entity;
        entity.setNombre(request.nombre());
        entity.setApellidoPaterno(request.apellidoPaterno());
        entity.setApellidoMaterno(request.apellidoMaterno());
        entity.setEmail(request.email());
        entity.setTelefono(request.telefono());
        entity.setDocumento(request.documento());
        entity.setNacionalidad(request.nacionalidad());
        return entity;
    }
}