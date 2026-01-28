import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import msv_reservas.entities.Reserva;

@Mapper(componentModel = "spring")
public interface ReservasMapper {

    // Ejemplo de mapeo explícito si los nombres de campos son distintos
    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "cliente.nombre", target = "nombreCliente")
    ReservaDTO toDTO(Reserva reserva);

    @Mapping(target = "cliente", ignore = true) // Se suele ignorar o manejar aparte al crear
    Reserva toEntity(ReservaDTO reservaDTO);
}