package com.jbyanx.gardenkeep.application.service;

import com.jbyanx.gardenkeep.application.port.in.WaterPotUseCase;
import com.jbyanx.gardenkeep.application.port.out.PotRepositoryPort;
import com.jbyanx.gardenkeep.domain.exception.PotNotFoundException;
import com.jbyanx.gardenkeep.domain.model.Pot;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
public class WaterPotService implements WaterPotUseCase {

    private final PotRepositoryPort potRepositoryPort;

    @Override
    public void execute(UUID potId, boolean isSoilDryAtSecondKnuckle) {
        // 1. Buscamos la maceta (el repositorio ya trae los crops por el FetchType y Cascade)
        Pot pot = potRepositoryPort.findById(potId)
                .orElseThrow(() -> new PotNotFoundException(potId));

        // 2. Ejecutamos la lógica de dominio
        pot.water(isSoilDryAtSecondKnuckle, LocalDateTime.now());

        // 3. Guardamos la maceta. JPA se encarga de actualizar los timestamps de cada Crop.
        potRepositoryPort.save(pot);
    }
}