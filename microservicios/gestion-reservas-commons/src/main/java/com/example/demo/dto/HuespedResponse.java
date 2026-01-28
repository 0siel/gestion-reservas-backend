package com.example.demo.dto;

import com.example.demo.enums.EstadoRegistro;
import com.example.demo.enums.Nacionalidad;

public record HuespedResponse(
	    Long id,
	    String nombre,
	    String apellidoPaterno,
	    String apellidoMaterno,
	    String email,
	    String telefono,
	    String documento,
	    Nacionalidad nacionalidad,
	    EstadoRegistro estadoRegistro
	) {}