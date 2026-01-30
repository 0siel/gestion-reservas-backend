package msv_huespedes.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.enums.EstadoRegistro;

import msv_huespedes.entities.Huesped;

@Repository
public interface HuespedRepository extends JpaRepository<Huesped, Long> {
  
    List<Huesped> findAllByEstadoRegistro(EstadoRegistro estadoRegistro);
  
    Optional<Huesped> findByIdAndEstadoRegistro(Long id, EstadoRegistro estadoRegistro);

    boolean existsByEmailAndEstadoRegistro(String email, EstadoRegistro estadoRegistro);

    boolean existsByTelefonoAndEstadoRegistro(String telefono, EstadoRegistro estadoRegistro);

    boolean existsByDocumentoAndEstadoRegistro(String documento, EstadoRegistro estadoRegistro);

    boolean existsByEmailAndIdNotAndEstadoRegistro(String email, Long id, EstadoRegistro estadoRegistro);

    boolean existsByTelefonoAndIdNotAndEstadoRegistro(String telefono, Long id, EstadoRegistro estadoRegistro);

    boolean existsByDocumentoAndIdNotAndEstadoRegistro(String documento, Long id, EstadoRegistro estadoRegistro);
}
