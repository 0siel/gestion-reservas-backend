package com.example.demo.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msv-reservas")
public interface ReservaClient {

    @GetMapping("/check-huesped/{id}")
    boolean tieneReservasHuesped(@PathVariable("id") Long id);

    @GetMapping("/reservas/check-habitacion/{id}")
    boolean tieneReservasHabitacion(@PathVariable("id") Long id);
}
