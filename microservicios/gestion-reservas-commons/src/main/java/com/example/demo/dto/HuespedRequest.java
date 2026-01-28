package com.example.demo.dto;

import jakarta.validation.constraints.*;

public record HuespedRequest(
    @NotBlank(message = "El nombre es requerido")
    @Size(max = 100)
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "Nombre inválido")
    String nombre,

    @NotBlank(message = "El apellido paterno es requerido")
    @Size(max = 100)
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "Apellido paterno inválido")
    String apellidoPaterno,

    @NotBlank(message = "El apellido materno es requerido")
    @Size(max = 100)
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "Apellido materno inválido")
    String apellidoMaterno,

    @NotBlank(message = "El email es requerido")
    @Email(message = "Formato de email inválido")
    String email,

    @NotBlank(message = "El teléfono es requerido")
    @Pattern(regexp = "^\\d{10}$", message = "El teléfono debe tener 10 dígitos")
    String telefono,

    @NotBlank(message = "El documento es requerido")
    String documento,

    @NotBlank(message = "La nacionalidad es requerida")
    String nacionalidad
) {
}