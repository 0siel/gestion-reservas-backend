package com.example.demo.clients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.demo.dto.HuespedResponse;

@FeignClient(name = "msv-huespedes")
public interface HuespedClient {

    
    @GetMapping("/{id}")
    HuespedResponse obtenerHuespedActivo(@PathVariable("id") Long id);

    
    @GetMapping("/historico/{id}")
    HuespedResponse obtenerHuespedHistorico(@PathVariable("id") Long id);
}
