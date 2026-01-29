package msv_huespedes.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.HuespedRequest;
import com.example.demo.dto.HuespedResponse;
import com.example.demo.enums.EstadoRegistro;
import com.example.demo.exceptions.RecursoDuplicadoException; // Import nuevo
import com.example.demo.exceptions.RecursoNoEncontradoException; // Import nuevo

import msv_huespedes.entities.Huesped;
import msv_huespedes.mappers.HuespedMapper;
import msv_huespedes.repositories.HuespedRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HuespedServiceImpl implements HuespedService {

    private final HuespedRepository huespedRepository;
    private final HuespedMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<HuespedResponse> listar() {
        return huespedRepository.findAllByEstadoRegistro(EstadoRegistro.ACTIVO).stream()
                .map(mapper::entityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public HuespedResponse obtenerPorId(Long id) {
        return mapper.entityToResponse(getEntityOrThrow(id));
    }

    @Override
    @Transactional
    public HuespedResponse registrar(HuespedRequest request) {
        validarUnicidad(request, null);

        Huesped huesped = mapper.requestToEntity(request);
        huesped.setEstadoRegistro(EstadoRegistro.ACTIVO);
        
        return mapper.entityToResponse(huespedRepository.save(huesped));
    }

    @Override
    @Transactional
    public HuespedResponse actualizar(HuespedRequest request, Long id) {
        Huesped huespedExistente = getEntityOrThrow(id);
        
        validarUnicidad(request, id);
        
        Huesped huespedActualizado = mapper.updateEntityFromRequest(request, huespedExistente);
        
        return mapper.entityToResponse(huespedRepository.save(huespedActualizado));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Huesped huesped = getEntityOrThrow(id);
        huesped.setEstadoRegistro(EstadoRegistro.ELIMINADO);
        huespedRepository.save(huesped);
    }

    private void validarUnicidad(HuespedRequest request, Long id) {
        boolean emailExiste;
        boolean telefonoExiste;
        boolean documentoExiste;

        if (id == null) {
            emailExiste = huespedRepository.existsByEmailAndEstadoRegistro(request.email(), EstadoRegistro.ACTIVO);
            telefonoExiste = huespedRepository.existsByTelefonoAndEstadoRegistro(request.telefono(), EstadoRegistro.ACTIVO);
            documentoExiste = huespedRepository.existsByDocumentoAndEstadoRegistro(request.documento(), EstadoRegistro.ACTIVO);
        } else {
            emailExiste = huespedRepository.existsByEmailAndIdNotAndEstadoRegistro(request.email(), id, EstadoRegistro.ACTIVO);
            telefonoExiste = huespedRepository.existsByTelefonoAndIdNotAndEstadoRegistro(request.telefono(), id, EstadoRegistro.ACTIVO);
            documentoExiste = huespedRepository.existsByDocumentoAndIdNotAndEstadoRegistro(request.documento(), id, EstadoRegistro.ACTIVO);
        }

        if (emailExiste) {
            throw new RecursoDuplicadoException("El email " + request.email() + " ya se encuentra registrado.");
        }
        if (telefonoExiste) {
            throw new RecursoDuplicadoException("El teléfono " + request.telefono() + " ya se encuentra registrado.");
        }
        if (documentoExiste) {
            throw new RecursoDuplicadoException("El documento " + request.documento() + " ya se encuentra registrado.");
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public HuespedResponse obtenerPorIdSinEstado(Long id) {
        // Busca por ID puro (ignora si está ELIMINADO o ACTIVO)
        Huesped huesped = huespedRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Huésped no encontrado en registros históricos con ID: " + id));
        
        return mapper.entityToResponse(huesped);
    }

    private Huesped getEntityOrThrow(Long id) {
        return huespedRepository.findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el huésped con ID: " + id));
    }
}