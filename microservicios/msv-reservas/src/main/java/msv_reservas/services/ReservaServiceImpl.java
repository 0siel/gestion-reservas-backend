package msv_reservas.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.ReservaRequest;
import com.example.demo.dto.ReservaResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {@Override
	public List<ReservaResponse> listar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReservaResponse obtenerPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReservaResponse registrar(ReservaRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReservaResponse actualizar(ReservaRequest request, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminar(Long id) {
		// TODO Auto-generated method stub
		
	}

}
