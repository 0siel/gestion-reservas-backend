package com.example.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EstadoHabitacion {

    DISPONIBLE(1L, "Lista para recibir nuevos huéspedes"),
    OCUPADA(2L, "Habitación con huéspedes actualmente"),
    LIMPIEZA(3L, "En proceso de aseo y preparación"),
    MANTENIMIENTO(4L, "Fuera de servicio por reparaciones");

    private final Long codigo;
    private final String descripcion;

    public static EstadoHabitacion fromCodigo(Long codigo) {
        for (EstadoHabitacion estado : EstadoHabitacion.values()) {
            if (estado.getCodigo().equals(codigo)) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Código de estado de habitación no válido: " + codigo);
    }
}