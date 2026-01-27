package com.example.demo.dto;

public record ErrorResponse(
        int codigo,
        String mensaje
) {
}