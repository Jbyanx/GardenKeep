package com.jbyanx.gardenkeep.application.port.out;

import com.jbyanx.gardenkeep.domain.model.Pot;

import java.util.Optional;
import java.util.UUID;

public interface PotRepositoryPort {
    void save(Pot pot);
    Optional<Pot> findById(UUID id);
}
