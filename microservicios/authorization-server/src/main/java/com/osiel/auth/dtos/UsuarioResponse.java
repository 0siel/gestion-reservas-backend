package com.osiel.auth.dtos;

import java.util.Set;

public record UsuarioResponse(
		Long id,
		String username,
		Set<String> roles
) {}
