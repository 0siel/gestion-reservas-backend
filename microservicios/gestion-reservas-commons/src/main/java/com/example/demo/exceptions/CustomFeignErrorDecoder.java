package com.example.demo.exceptions;
// Ajusta a tu paquete de commons

import com.example.demo.dto.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;

public class CustomFeignErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        String mensaje;
        
        try (InputStream bodyIs = response.body().asInputStream()) {
            // Al ser un Record, Jackson lo mapea igual, 
            // pero nosotros lo accedemos distinto
            ErrorResponse errorBody = objectMapper.readValue(bodyIs, ErrorResponse.class);
            
            // CAMBIO AQUÍ: Usamos mensaje() en lugar de getMensaje()
            mensaje = errorBody.mensaje(); 
        } catch (IOException | NullPointerException e) {
            mensaje = "Error al procesar la respuesta del servicio externo";
        }

        return switch (response.status()) {
            case 404 -> new NoSuchElementException(mensaje);
            case 400 -> new IllegalArgumentException(mensaje);
            default -> new Exception("Error: " + mensaje);
        };
    }
}
