package msv_habitaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"msv_habitaciones", "com.example.demo"})
@EnableDiscoveryClient
public class MsvHabitacionesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsvHabitacionesApplication.class, args);
    }
}
