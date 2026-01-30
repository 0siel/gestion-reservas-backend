package com.example.demo.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.HabitacionResponse;

@FeignClient(name = "msv.habitaciones")
public interface HabitacionClient {

    
    @GetMapping("/{id}")
    HabitacionResponse obtenerHabitacionActiva(@PathVariable("id") Long id);

    
    @GetMapping("/historico/{id}")
    HabitacionResponse obtenerHabitacionHistorico(@PathVariable("id") Long id);

    
    @PutMapping("/{id}/cambiar-estado")
    void cambiarEstadoHabitacion(@PathVariable("id") Long id, @RequestParam("estado") String estado);
}
