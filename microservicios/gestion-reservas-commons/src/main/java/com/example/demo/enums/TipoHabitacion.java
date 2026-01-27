package com.example.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoHabitacion {

    INDIVIDUAL(1L, "Habitación sencilla para una persona"),
    DOBLE(2L, "Habitación estándar para dos personas"),
    SUITE(3L, "Habitación de lujo con servicios adicionales");

    private final Long codigo;
    private final String descripcion;

    public static TipoHabitacion fromCodigo(Long codigo) {
        for (TipoHabitacion tipo : TipoHabitacion.values()) {
            if (tipo.getCodigo().equals(codigo)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Código de tipo de habitación no válido: " + codigo);
    }
}
