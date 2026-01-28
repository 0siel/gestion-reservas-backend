package com.example.demo.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record HuespedRequest(

        @NotBlank(message = "El nombre es requerido")
        @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
        String nombre,

        @NotBlank(message = "El apellido es requerido")
        @Size(max = 100, message = "El apellido no puede exceder los 100 caracteres")
        String apellido,

        @NotBlank(message = "El email es requerido")
        @Email(message = "Formato de email inválido")
        @Size(max = 150, message = "El email no puede exceder los 150 caracteres")
        String email,

        @NotBlank(message = "El documento es requerido")
        @Size(max = 50, message = "El documento no puede exceder los 50 caracteres")
        String documento,

        @NotBlank(message = "La nacionalidad es requerida")
        @Size(max = 50, message = "La nacionalidad no puede exceder los 50 caracteres")
        String nacionalidad
        
) {
}
