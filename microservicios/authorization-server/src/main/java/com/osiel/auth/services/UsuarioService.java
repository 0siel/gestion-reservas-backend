package com.osiel.auth.services;


import java.util.Set;

import com.osiel.auth.dtos.UsuarioRequest;
import com.osiel.auth.dtos.UsuarioResponse;



public interface UsuarioService {
	
	Set<UsuarioResponse> listar();
	
	UsuarioResponse registrar(UsuarioRequest request);
	
	UsuarioResponse eliminar(String username);

}
