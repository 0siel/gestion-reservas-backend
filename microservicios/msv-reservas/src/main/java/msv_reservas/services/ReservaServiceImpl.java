package msv_reservas.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.ReservaRequest;
import com.example.demo.dto.ReservaResponse;

import lombok.RequiredArgsConstructor;
import msv_reservas.entities.Reserva;
import msv_reservas.mappers.ReservaMapper;
import msv_reservas.repositories.ReservaRepository;


@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {
	
	
	   private final ReservaRepository reservaRepository;
	   private final ReservaMapper mapper;
	
	
	@Override
    @Transactional(readOnly = true)
	public List<ReservaResponse> listar() {
	 return reservaRepository.findAll().stream()
             .map(mapper::entityToResponse)
             .collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public ReservaResponse obtenerPorId(Long id) {
		return mapper.entityToResponse(getEntityOrThrow(id));
	}

	@Override
	@Transactional
	public ReservaResponse registrar(ReservaRequest request) {
	Reserva reserva = mapper.requestToEntity(request);
    return mapper.entityToResponse(reservaRepository.save(reserva));
		
	}

	@Override
	@Transactional
	public ReservaResponse actualizar(ReservaRequest request, Long id) {
		Reserva reservaExistente = getEntityOrThrow(id);
        Reserva reservaActualizado = mapper.updateEntityFromRequest(request, reservaExistente);
        return mapper.entityToResponse(reservaRepository.save(reservaActualizado));
	}

	@Override
	@Transactional
	public void eliminar(Long id) {
		Reserva reserva = getEntityOrThrow(id);
        reservaRepository.delete(reserva);
		
	}
	
	private Reserva getEntityOrThrow(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con ID: " + id));

}
}
