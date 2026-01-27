package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record MedicoRequest(

	    @NotBlank(message = "El nombre es requerido")
	    @Size(max = 50)
	    String nombre,

	    @NotBlank(message = "El apellido paterno es requerido")
	    @Size(max = 50)
	    String apellidoPaterno,

	    @NotBlank(message = "El apellido materno es requerido")
	    @Size(max = 50)
	    String apellidoMaterno,

	    @NotNull(message = "La edad es requerida")
	    @Min(value = 18, message = "El médico debe ser mayor de 18 años")
	    Integer edad,

	    @NotBlank(message = "El email es requerido")
	    @Email(message = "Formato de email inválido")
	    @Size(max = 100)
	    String email,

	    @NotBlank(message = "El teléfono es requerido")
	    @Pattern(regexp = "^[0-9]{10}$", message = "El teléfono debe tener 10 dígitos")
	    String telefono,

	    @NotBlank(message = "La cédula profesional es requerida")
	    @Size(max = 12)
	    String cedulaProfesional,

	    @NotNull(message = "La especialidad es requerida")
	    @Positive(message = "El id de la disponibilidad debe ser positivo")
	    Long idEspecialidad,

	    @Positive(message = "El id de la disponilidad debe ser positivo")
	    Long idDisponibilidad
		
		) {

}
