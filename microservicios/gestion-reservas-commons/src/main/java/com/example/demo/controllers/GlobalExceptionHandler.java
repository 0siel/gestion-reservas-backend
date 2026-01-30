package com.example.demo.controllers;

import com.example.demo.exceptions.HabitacionNoDisponibleException;
import com.example.demo.exceptions.RecursoDuplicadoException;
import com.example.demo.exceptions.RecursoEnUsoException;
import com.example.demo.exceptions.RecursoNoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // -------------------------------------------------------------------
    // 1. Manejo de CONFLICTO (409) - Duplicidad de datos
    // -------------------------------------------------------------------
    @ExceptionHandler(RecursoDuplicadoException.class)
    public ResponseEntity<Map<String, Object>> handleRecursoDuplicado(RecursoDuplicadoException ex) {
        return buildResponse(HttpStatus.CONFLICT, "Conflicto de Datos", ex.getMessage());
    }

    // -------------------------------------------------------------------
    // 2. Manejo de NO ENCONTRADO (404) - ID inexistente o borrado lógico
    // -------------------------------------------------------------------
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleRecursoNoEncontrado(RecursoNoEncontradoException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "Recurso No Encontrado", ex.getMessage());
    }

    // -------------------------------------------------------------------
    // 3. Manejo de VALIDACIONES DE CAMPOS (400) - @NotNull, @Email, etc.
    // -------------------------------------------------------------------
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        
        // Extraemos campo por campo para decir exactamente qué falló
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Error de Validación");
        response.put("detalles", errors); // Devuelve un mapa detallado

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // -------------------------------------------------------------------
    // 4. Manejo de ERROR DE FORMATO JSON (400) - Enums inválidos
    // -------------------------------------------------------------------
    // Esto pasa si envían "nacionalidad": "MARTE" y no existe en el Enum
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleJsonErrors(HttpMessageNotReadableException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Formato JSON Inválido", 
                "El cuerpo de la petición no es válido. Verifique los tipos de datos y los valores de los Enums (ej. Nacionalidad).");
    }
    
    @ExceptionHandler(RecursoEnUsoException.class)
    public ResponseEntity<Map<String, Object>> handleRecursoEnUso(RecursoEnUsoException ex) {
        return buildResponse(HttpStatus.CONFLICT, "No se puede eliminar el recurso", ex.getMessage());
    }
    
 // Manejo para cuando la habitación está ocupada/mantenimiento
    @ExceptionHandler(HabitacionNoDisponibleException.class)
    public ResponseEntity<Map<String, Object>> handleHabitacionNoDisponible(HabitacionNoDisponibleException ex) {
        return buildResponse(HttpStatus.CONFLICT, "Conflicto de Disponibilidad", ex.getMessage());
    }

    // -------------------------------------------------------------------
    // 5. Manejo de ERROR INTERNO (500) - Fallos inesperados
    // -------------------------------------------------------------------
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(Exception ex) {
        ex.printStackTrace(); // Importante para ver el error real en los logs del servidor
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error Interno del Servidor", 
                "Ocurrió un error inesperado. Contacte al administrador.");
    }

    // Método helper para construir la respuesta JSON estándar
    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String error, String mensaje) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", status.value());
        response.put("error", error);
        response.put("mensaje", mensaje);
        return new ResponseEntity<>(response, status);
    }
}