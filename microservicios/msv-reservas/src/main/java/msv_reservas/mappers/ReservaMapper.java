package msv_reservas.mappers;

import org.springframework.stereotype.Component;

import com.example.demo.clients.HabitacionClient;
import com.example.demo.clients.HuespedClient;
import com.example.demo.dto.HabitacionResponse;
import com.example.demo.dto.HuespedResponse;
import com.example.demo.dto.ReservaRequest;
import com.example.demo.dto.ReservaResponse;
import com.example.demo.enums.EstadoRegistro;
import com.example.demo.enums.EstadoReserva;
import com.example.demo.mappers.CommonMapper; // Asumiendo que tienes esta interfaz

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import msv_reservas.entities.Reserva;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReservaMapper implements CommonMapper<ReservaRequest, ReservaResponse, Reserva> {

    // 1. Inyectamos los clientes Feign
    private final HabitacionClient habitacionClient;
    private final HuespedClient huespedClient;

    @Override
    public ReservaResponse entityToResponse(Reserva entity) {
        if (entity == null) return null;

        // 2. Obtener Huesped (Usamos histórico para no fallar si está eliminado)
        HuespedResponse huespedResponse = null;
        try {
            // Feign Client call
            huespedResponse = huespedClient.obtenerHuespedHistorico(entity.getIdHuesped());
        } catch (Exception e) {
            log.warn("No se pudo obtener datos del huésped ID {}: {}", entity.getIdHuesped(), e.getMessage());
            // Opcional: Podrías crear un objeto dummy o dejarlo en null
        }

        // 3. Obtener Habitación (Usamos histórico)
        HabitacionResponse habitacionResponse = null;
        try {
            // Feign Client call
            habitacionResponse = habitacionClient.obtenerHabitacionHistorico(entity.getIdHabitacion());
        } catch (Exception e) {
            log.warn("No se pudo obtener datos de la habitación ID {}: {}", entity.getIdHabitacion(), e.getMessage());
        }

        // 4. Construir la respuesta enriquecida
        return new ReservaResponse(
            entity.getId(),
            huespedResponse,      // Objeto completo en lugar de ID
            habitacionResponse,   // Objeto completo en lugar de ID
            entity.getFechaEntrada(),
            entity.getFechaSalida(),
            entity.getCantNoches(),
            entity.getMontoTotal(),
            entity.getEstado() != null ? entity.getEstado().name() : null,
            entity.getFechaCreacion()
        );
    }

    @Override
    public Reserva requestToEntity(ReservaRequest request) {
        if (request == null) return null;

        Reserva reserva = new Reserva();
        // Mapeo inverso: Del Request (IDs) a la Entidad (IDs)
        reserva.setIdHuesped(request.idHuesped());
        reserva.setIdHabitacion(request.idHabitacion());
        reserva.setFechaEntrada(request.fechaEntrada());
        reserva.setFechaSalida(request.fechaSalida());
        
        // Valores por defecto al crear (serán sobrescritos por la lógica del servicio)
        reserva.setEstado(EstadoReserva.CONFIRMADA);
        reserva.setEstadoRegistro(EstadoRegistro.ACTIVO);
        
        return reserva;
    }

    @Override
    public Reserva updateEntityFromRequest(ReservaRequest request, Reserva entity) {
        if (entity == null || request == null) return null;

        entity.setIdHuesped(request.idHuesped());
        entity.setIdHabitacion(request.idHabitacion());
        entity.setFechaEntrada(request.fechaEntrada());
        entity.setFechaSalida(request.fechaSalida());
        
        // No actualizamos estado ni estadoRegistro aquí, eso es lógica de negocio
        
        return entity;
    }
}