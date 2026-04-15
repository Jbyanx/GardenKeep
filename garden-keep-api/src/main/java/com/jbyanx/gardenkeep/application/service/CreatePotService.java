package com.jbyanx.gardenkeep.application.service;

import com.jbyanx.gardenkeep.application.port.in.CreatePotUseCase;
import com.jbyanx.gardenkeep.application.port.out.PotRepositoryPort;
import com.jbyanx.gardenkeep.domain.model.Pot;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class CreatePotService implements CreatePotUseCase {

    private final PotRepositoryPort potRepositoryPort;

    @Override
    public UUID execute(String name, String description) {
        // Usamos el mét odo de fábrica que definimos en el Dominio
        Pot newPot = Pot.createNew(name, description);

        potRepositoryPort.save(newPot);

        return newPot.getId();
    }
}