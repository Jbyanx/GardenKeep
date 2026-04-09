package com.jbyanx.gardenkeep.infrastructure.adapter.out.persistence;

import com.jbyanx.gardenkeep.application.port.out.CropRepositoryPort;
import com.jbyanx.gardenkeep.domain.model.Crop;
import com.jbyanx.gardenkeep.domain.model.CropType;
import com.jbyanx.gardenkeep.domain.model.GrowthStage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component // Este sí es un componente de Spring
public class InMemoryCropRepositoryAdapter implements CropRepositoryPort {

    private final Map<UUID, Crop> database = new HashMap<>();

    public InMemoryCropRepositoryAdapter() {
        // Insertamos un cultivo de prueba para poder jugar con la API
        UUID testId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        database.put(testId, new Crop(testId, CropType.ONION, GrowthStage.PHASE_1_SURFACE, null));
    }

    @Override
    public Optional<Crop> findById(UUID id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public void save(Crop crop) {
        database.put(crop.getId(), crop);
        System.out.println(">>> Guardado en memoria: " + crop.getType() + " con fecha " + crop.getLastWateredAt());
    }
}