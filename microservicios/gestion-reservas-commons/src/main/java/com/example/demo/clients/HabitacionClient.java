package com.example.demo.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.dto.HabitacionRequest;
import com.example.demo.dto.HabitacionResponse;

import java.util.List;

@FeignClient(name = "msv.habitaciones")
public interface HabitacionClient {

	//@GetMapping("/")
	//List<HabitacionResponse> listarHabitaciones();

	@GetMapping("/{id}")
	HabitacionResponse obtenerHabitacionPorId(@PathVariable Long id);

	@GetMapping("/id-habitacion/{id}")
	HabitacionResponse obtenerHabitacionSinEstado(@PathVariable Long id);

	@PutMapping("/{id}")
	HabitacionResponse actualizarHabitacion(@PathVariable Long id, @RequestBody HabitacionRequest request);
}
