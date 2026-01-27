package msv_reservas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"msv_reservas", "com.example.demo"})
public class MsvReservasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvReservasApplication.class, args);
	}

}
