package msv_huespedes.services;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.HuespedRequest;
import com.example.demo.dto.HuespedResponse;

import msv_huespedes.entities.Huesped;
import msv_huespedes.mappers.HuespedMapper;
import msv_huespedes.repositories.HuespedRepository;
import msv_huespedes.services.HuespedService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HuespedServiceImpl implements HuespedService {

    private final HuespedRepository huespedRepository;
    private final HuespedMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<HuespedResponse> listar() {
        return huespedRepository.findAll().stream()
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
        Huesped huesped = mapper.requestToEntity(request);
        return mapper.entityToResponse(huespedRepository.save(huesped));
    }

    @Override
    @Transactional
    public HuespedResponse actualizar(HuespedRequest request, Long id) {
        Huesped huespedExistente = getEntityOrThrow(id);
        Huesped huespedActualizado = mapper.updateEntityFromRequest(request, huespedExistente);
        return mapper.entityToResponse(huespedRepository.save(huespedActualizado));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Huesped huesped = getEntityOrThrow(id);
        huespedRepository.delete(huesped);
    }

    /**
     * Método de soporte para validar la existencia del huésped.
     * Lanza una excepción si el ID no existe en el esquema HUESPEDES_USER.
     */
    private Huesped getEntityOrThrow(Long id) {
        return huespedRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Huésped no encontrado con ID: " + id));
    }
}
