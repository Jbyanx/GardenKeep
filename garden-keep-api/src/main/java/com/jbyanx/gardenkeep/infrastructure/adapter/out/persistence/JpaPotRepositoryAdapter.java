package com.jbyanx.gardenkeep.infrastructure.adapter.out.persistence;

import com.jbyanx.gardenkeep.application.port.out.PotRepositoryPort;
import com.jbyanx.gardenkeep.domain.model.Pot;
import com.jbyanx.gardenkeep.infrastructure.adapter.out.persistence.mapper.PotPersistenceMapper;
import com.jbyanx.gardenkeep.infrastructure.adapter.out.persistence.repository.JpaPotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JpaPotRepositoryAdapter implements PotRepositoryPort {
    private final JpaPotRepository jpaRepository;
    private final PotPersistenceMapper mapper;

    @Override
    public void save(Pot pot) {
        jpaRepository.save(mapper.toEntity(pot)); // Convertimos a PotEntity y guardamos
    }

    @Override
    public Optional<Pot> findById(UUID id) {
        return jpaRepository.findById(id) //optional de PotEntity
                .map(mapper::toDomain); // Convertimos a Pot
    }
}
