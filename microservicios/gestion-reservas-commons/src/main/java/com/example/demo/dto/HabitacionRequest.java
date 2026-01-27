package com.example.demo.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record HabitacionRequest(
    @NotBlank(message = "El número de habitación es requerido")
    @Size(max = 10, message = "El número puede tener máximo 10 caracteres")
    String numero,

    @NotNull(message = "El tipo de habitación es requerido")
    @Positive(message = "El id del tipo debe ser positivo")
    Long idTipo,

    @NotNull(message = "El precio por noche es requerido")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    BigDecimal precioNoche,

    @NotNull(message = "La capacidad es requerida")
    @Min(value = 1, message = "La capacidad mínima es 1 persona")
    Integer capacidad,

    @Positive(message = "El id del estado debe ser positivo")
    Long idEstado
) {}
