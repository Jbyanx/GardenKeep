package com.jbyanx.gardenkeep.infrastructure.adapter.out.persistence;

import com.jbyanx.gardenkeep.application.port.out.CropRepositoryPort;
import com.jbyanx.gardenkeep.domain.model.Crop;
import com.jbyanx.gardenkeep.infrastructure.adapter.out.persistence.mapper.CropPersistenceMapper;
import com.jbyanx.gardenkeep.infrastructure.adapter.out.persistence.repository.JpaCropRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component //para que spring lo maneje
@RequiredArgsConstructor //para las dependencias (DI)
public class PostgresCropRepositoryAdapter implements CropRepositoryPort {
    private final JpaCropRepository jpaRepository;
    private final CropPersistenceMapper mapper;

    @Override
    public Optional<Crop> findById(UUID id) {
        return jpaRepository.findById(id) //optional de Crop
                .map(mapper::toDomain); // Convertimos a CropEntity
    }

    @Override
    public void save(Crop crop) {
        jpaRepository.save(mapper.toEntity(crop)); // Convertimos a CropEntity y guardamos
    }
}
