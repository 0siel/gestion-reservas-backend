package msv_reservas.repositories;

import com.example.demo.enums.EstadoRegistro;
import msv_reservas.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    
    // Buscar todas las reservas que NO han sido borradas lógicamente
    List<Reserva> findAllByEstadoRegistro(EstadoRegistro estadoRegistro);
    
    // Buscar por ID asegurando que esté ACTIVO
    Optional<Reserva> findByIdAndEstadoRegistro(Long id, EstadoRegistro estadoRegistro);
}
