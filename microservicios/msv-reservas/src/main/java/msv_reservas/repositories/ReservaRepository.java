package msv_reservas.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.enums.EstadoRegistro;

import msv_reservas.entities.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
	
Optional<Reserva> findByIdAndEstadoRegistro(Long id, EstadoRegistro estadoRegistro);

}
