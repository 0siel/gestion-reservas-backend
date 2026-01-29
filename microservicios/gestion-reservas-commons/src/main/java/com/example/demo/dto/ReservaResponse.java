package com.example.demo.dto;



import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReservaResponse(
        Long id,
        Long idHuesped,
        Long idHabitacion,
        LocalDate fechaEntrada,
        LocalDate fechaSalida,
        Integer cantNoches,
        BigDecimal montoTotal,
        String estado,
        LocalDateTime fechaCreacion
) {
}
