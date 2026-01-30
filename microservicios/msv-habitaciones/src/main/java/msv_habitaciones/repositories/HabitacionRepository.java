package msv_habitaciones.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.enums.EstadoRegistro;
import msv_habitaciones.entities.Habitacion;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {

    // --- BÚSQUEDAS (Filtrando por ACTIVO) ---
    List<Habitacion> findAllByEstadoRegistro(EstadoRegistro estadoRegistro);
    
    Optional<Habitacion> findByIdAndEstadoRegistro(Long id, EstadoRegistro estadoRegistro);

    // --- VALIDACIONES DE UNICIDAD ---
    
    // Para registrar: ¿Ya existe el número en una habitación activa?
    boolean existsByNumeroAndEstadoRegistro(String numero, EstadoRegistro estadoRegistro);

    // Para actualizar: ¿Existe el número en OTRA habitación activa (id diferente)?
    boolean existsByNumeroAndIdNotAndEstadoRegistro(String numero, Long id, EstadoRegistro estadoRegistro);
}
