package msv_huespedes.controllers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.dto.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class LocalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        // Obtenemos la causa raíz (el error ORA de Oracle)
        String rootMsg = e.getMostSpecificCause().getMessage();
        log.error("Violación de integridad detectada: {}", rootMsg);

        String mensajeDescriptivo = "Error de integridad: El registro ya existe.";

        // Lógica para formatear un mensaje amigable
        if (rootMsg.contains("ORA-00001")) {
            if (rootMsg.contains("EMAIL")) {
                mensajeDescriptivo = "El correo electrónico ya se encuentra registrado.";
            } else if (rootMsg.contains("DOCUMENTO")) {
                mensajeDescriptivo = "El número de documento ya pertenece a otro huésped.";
            } else {
                mensajeDescriptivo = "Ya existe un registro con estos datos únicos.";
            }
        }

        ErrorResponse response = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(), 
            mensajeDescriptivo
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
