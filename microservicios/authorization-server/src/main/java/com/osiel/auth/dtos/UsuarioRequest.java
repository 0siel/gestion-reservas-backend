package com.osiel.auth.dtos;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioRequest(
		@NotBlank(message = "El username es requerido")
		@Size(min = 4, max = 20, message = "El username debe tener entre 4 y 20 caracteres")
		String username,
		@NotBlank(message = "La contraseña es requerida")
		@Size(min = 8, max = 20, message = "La contraseña debe tener entre 8 y 20 caracteres")
		String password,
		@NotNull(message = "Los roles son requeridos")
		@Size(min = 1, message = "El usuario debe tener al menos 1 rol")
		Set<String> roles
) {}
