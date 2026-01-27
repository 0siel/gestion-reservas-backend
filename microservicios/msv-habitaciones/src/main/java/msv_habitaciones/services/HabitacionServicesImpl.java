package msv_habitaciones.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.HabitacionRequest;
import com.example.demo.dto.HabitacionResponse;
import com.example.demo.enums.EstadoRegistro;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import msv_habitaciones.entities.Habitacion;
import msv_habitaciones.mappers.HabitacionMapper;
import msv_habitaciones.repositories.HabitacionRepository;

@Service
@AllArgsConstructor
@Slf4j
class HabitacionServiceImpl implements HabitacionService {

    private final HabitacionRepository repository;
    private final HabitacionMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<HabitacionResponse> listar() {
        log.info("Listando todas las habitaciones activas");
        return repository.findByEstadoRegistro(EstadoRegistro.ACTIVO).stream()
            .map(mapper::entityToResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public HabitacionResponse obtenerPorId(Long id) {
        log.info("Buscando habitación con id: {}", id);
        return repository.findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO)
            .map(mapper::entityToResponse)
            .orElseThrow(() -> new NoSuchElementException("Habitación no encontrada con id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public HabitacionResponse obtenerHabitacionPorIdSinEstado(Long id) {
        log.info("Buscando habitación sin validar estado con id: {}", id);
        return mapper.entityToResponse(repository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Habitación no encontrada con id: " + id)));
    }

    @Override
    @Transactional
    public HabitacionResponse registrar(HabitacionRequest request) {
        log.info("Registrando nueva habitación: {}", request.numero());

        existsByNumero(request.numero());

        Habitacion habitacion = repository.save(mapper.requestToEntity(request));

        log.info("Habitación registrada con éxito: {}", habitacion);
        return mapper.entityToResponse(habitacion);
    }

    @Override
    @Transactional
    public HabitacionResponse actualizar(HabitacionRequest request, Long id) {
        Habitacion habitacion = getHabitacionOrThrow(id);
        log.info("Actualizando habitación: {}", habitacion.getNumero());

        validarNumeroCambio(habitacion, request);

        Habitacion actualizada = repository.save(mapper.updateEntityFromRequest(request, habitacion));

        log.info("Habitación actualizada con éxito: {}", actualizada);
        return mapper.entityToResponse(actualizada);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Habitacion habitacion = getHabitacionOrThrow(id);
        log.info("Eliminando habitación con id: {}", id);

        habitacion.setEstadoRegistro(EstadoRegistro.ELIMINADO);
        repository.save(habitacion);

        log.info("Habitación con id: {} ha sido marcada como eliminada", id);
    }

    private Habitacion getHabitacionOrThrow(Long id) {
        log.info("Buscando habitación con id: {}", id);
        return repository.findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO)
            .orElseThrow(() -> new NoSuchElementException("Habitación no encontrada con id: " + id));
    }

    private void existsByNumero(String numero) {
        if (repository.existsByNumeroAndEstadoRegistro(numero, EstadoRegistro.ACTIVO)) {
            throw new IllegalArgumentException("Ya existe una habitación con ese número: " + numero);
        }
    }

    private void validarNumeroCambio(Habitacion habitacion, HabitacionRequest request) {
        if (!habitacion.getNumero().equalsIgnoreCase(request.numero())) {
            existsByNumero(request.numero());
        }
    }
}
