package msv_huespedes.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controllers.CommonController;
import com.example.demo.dto.HuespedRequest;
import com.example.demo.dto.HuespedResponse;

import msv_huespedes.services.HuespedService;

@RestController
public class HuespedController extends CommonController<HuespedRequest, HuespedResponse, HuespedService> {

    public HuespedController(HuespedService service) {
        super(service);
    }
    
    @GetMapping("/test")
    public String test() {
        return "El controlador funciona";
    }
    
    
}
