package com.example.demo.dto;

public record HuespedResponse(
    Long id,
    String nombre,
    String apellidoPaterno,
    String apellidoMaterno,
    String email,
    String telefono,
    String documento,
    String nacionalidad
) {
}