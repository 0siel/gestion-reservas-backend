package com.example.demo.dto;

import java.math.BigDecimal;

public record HabitacionResponse(
    Long id,
    String numero,
    String tipo,
    BigDecimal precioNoche,
    Integer capacidad,
    String estado
) {}
