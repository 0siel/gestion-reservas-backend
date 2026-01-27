package msv_reservas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import msv_reservas.entities.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

}
