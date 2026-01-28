package com.example.demo.controllers;

import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.demo.services.CrudService;

import java.util.List;

@Validated
public class CommonController <RQ, RS, S extends CrudService<RQ, RS>>{
    protected S service;
    
    public CommonController(S service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<RS>> listar(){
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RS> obtenerPorId(@PathVariable @Positive(message = "El id debe ser positivo") Long id){
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<RS> registar(@Validated @RequestBody RQ request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RS> actualizar(@PathVariable @Positive(message = "El ID debe ser positivo") Long id, @Validated @RequestBody RQ request){
        return ResponseEntity.ok(service.actualizar(request, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RS> eliminar(@PathVariable @Positive(message = "El ID debe ser positivo") Long id){
        service.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}

