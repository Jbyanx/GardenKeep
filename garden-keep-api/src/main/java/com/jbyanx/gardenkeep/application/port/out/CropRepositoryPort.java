package com.jbyanx.gardenkeep.application.port.out;

import com.jbyanx.gardenkeep.domain.model.Crop;

import java.util.Optional;
import java.util.UUID;

public interface CropRepositoryPort {
    //busca el cultivo por su id
    Optional<Crop> findById(UUID id);
    //guarda
    void save(Crop crop);
}