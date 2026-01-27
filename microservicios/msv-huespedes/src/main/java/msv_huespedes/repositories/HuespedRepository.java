package msv_huespedes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import msv_huespedes.entities.Huesped;

public interface HuespedRepository extends JpaRepository<Huesped, Long> {

}
