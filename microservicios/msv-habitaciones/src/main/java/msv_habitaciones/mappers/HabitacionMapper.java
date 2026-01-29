package msv_habitaciones.mappers;

import org.springframework.stereotype.Component;

import com.example.demo.dto.HabitacionRequest;
import com.example.demo.dto.HabitacionResponse;
import com.example.demo.enums.EstadoHabitacion;
import com.example.demo.enums.EstadoRegistro;
import com.example.demo.enums.TipoHabitacion;
import com.example.demo.mappers.CommonMapper;

import msv_habitaciones.entities.Habitacion;

@Component
public class HabitacionMapper implements CommonMapper<HabitacionRequest, HabitacionResponse, Habitacion> {

    @Override
    public HabitacionResponse entityToResponse(Habitacion entity) {
        if (entity == null) return null;

        return new HabitacionResponse(
            entity.getId(),
            entity.getNumero(),
            entity.getTipo().name(),
            entity.getPrecioNoche(),
            entity.getCapacidad(),
            entity.getEstado().name()
        );
    }

    @Override
    public Habitacion requestToEntity(HabitacionRequest request) {
        if (request == null) return null;

        Habitacion habitacion = new Habitacion();
        habitacion.setNumero(request.numero());
        habitacion.setTipo(TipoHabitacion.fromCodigo(request.idTipo()));
        habitacion.setPrecioNoche(request.precioNoche());
        habitacion.setCapacidad(request.capacidad());
        habitacion.setEstado(EstadoHabitacion.DISPONIBLE);
        habitacion.setEstadoRegistro(EstadoRegistro.ACTIVO);
        return habitacion;
    }

    @Override
    public Habitacion updateEntityFromRequest(HabitacionRequest request, Habitacion entity) {
        if (entity == null || request == null) return null;

        entity.setNumero(request.numero());
        entity.setTipo(TipoHabitacion.fromCodigo(request.idTipo()));
        entity.setPrecioNoche(request.precioNoche());
        entity.setCapacidad(request.capacidad());
        
        if (request.idEstado() != null) {
            entity.setEstado(EstadoHabitacion.fromCodigo(request.idEstado()));
        }
        
        return entity;
    }
}
