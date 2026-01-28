package com.osiel.auth.services;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.osiel.auth.dtos.UsuarioRequest;
import com.osiel.auth.dtos.UsuarioResponse;
import com.osiel.auth.entities.Rol;
import com.osiel.auth.entities.Usuario;
import com.osiel.auth.mappers.UsuarioMapper;
import com.osiel.auth.repositories.RolRepository;
import com.osiel.auth.repositories.UsuarioRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class UsuarioServiceImpl implements UsuarioService {
	
	private final UsuarioRepository usuarioRepository;
	
	private final RolRepository rolRepository;
	
	private final UsuarioMapper usuarioMapper;
	
	private final PasswordEncoder passwordEncoder;
	
	@Override
	@Transactional(readOnly = true)
	public Set<UsuarioResponse> listar() {
		log.info("Listado de todos los usuarios solicitado");
		return usuarioRepository.findAll().stream()
				.map(usuarioMapper::entityToResponse).collect(Collectors.toSet());
	}

	@Override
	public UsuarioResponse registrar(UsuarioRequest request) {
		log.info("Buscando usuario {}", request.username());
		if (usuarioRepository.findByUsername(request.username()).isPresent()) {
			throw new IllegalArgumentException("El usuario " + request.username() + " ya está registrado");
		}
		
		log.info("Registrando usuario {}", request.username());
		Set<Rol> roles = request.roles().stream().map(rol ->
				rolRepository.findByNombre(rol).orElseThrow( () ->
					new NoSuchElementException("Rol " + rol + " no encontrado") )
				).collect(Collectors.toSet());
		Usuario usuario = usuarioMapper.requestToEntity(request,
				passwordEncoder.encode(request.password()),
				roles
		);
		
		usuario = usuarioRepository.save(usuario);
		log.info("Usuario registrado {}", usuario.getUsername());
		return usuarioMapper.entityToResponse(usuario);
	}

	@Override
	public UsuarioResponse eliminar(String username) {
		log.info("Buscando usuario {}", username);
		Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow(() ->
			new NoSuchElementException("No se encontró el usuario: " + username)
		);
		usuarioRepository.delete(usuario);
		log.info("Usuario {} eliminado", usuario.getUsername());
		return usuarioMapper.entityToResponse(usuario);
	}

}