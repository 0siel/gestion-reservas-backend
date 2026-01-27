package com.example.demo.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ReservaRequest(
        @NotNull(message = "El id del huésped es requerido")
        @Positive(message = "El id del huésped debe ser positivo")
        Long idHuesped,

        @NotNull(message = "El id de la habitación es requerido")
        @Positive(message = "El id de la habitación debe ser positivo")
        Long idHabitacion,

        @NotNull(message = "La fecha de entrada es requerida")
        @FutureOrPresent(message = "La fecha de entrada no puede ser en el pasado")
        LocalDate fechaEntrada,

        @NotNull(message = "La fecha de salida es requerida")
        LocalDate fechaSalida
) {
}
