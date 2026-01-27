package com.example.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EstadoReserva {

    CONFIRMADA(1L, "Reserva registrada y garantizada"),
    EN_CURSO(2L, "Huésped ha realizado el Check-in"),
    FINALIZADA(3L, "Huésped ha realizado el Check-out"),
    CANCELADA(4L, "Reserva anulada por el cliente o el hotel");

    private final Long codigo;
    private final String descripcion;

    public static EstadoReserva fromCodigo(Long codigo) {
        for (EstadoReserva estado : EstadoReserva.values()) {
            if (estado.getCodigo().equals(codigo)) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Código de estado de reserva no válido: " + codigo);
    }
}