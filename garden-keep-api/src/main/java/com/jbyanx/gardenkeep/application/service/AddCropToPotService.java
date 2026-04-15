package com.jbyanx.gardenkeep.application.service;

import com.jbyanx.gardenkeep.application.port.in.AddCropToPotUseCase;
import com.jbyanx.gardenkeep.application.port.out.PotRepositoryPort;
import com.jbyanx.gardenkeep.domain.exception.PotNotFoundException;
import com.jbyanx.gardenkeep.domain.model.Crop;
import com.jbyanx.gardenkeep.domain.model.CropType;
import com.jbyanx.gardenkeep.domain.model.GrowthStage;
import com.jbyanx.gardenkeep.domain.model.Pot;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class AddCropToPotService implements AddCropToPotUseCase {
    private final PotRepositoryPort potRepository;

    @Override
    public UUID execute(UUID potId, CropType type) {
        //buscamos la maceta
        Pot pot = potRepository.findById(potId)
                .orElseThrow(() -> new PotNotFoundException(potId));

        Crop newCrop = new Crop(
                UUID.randomUUID(),
                type,
                GrowthStage.PHASE_1_SURFACE, //quemado por ahora pero se podría transplantar mas adelante, pero no se si al transplantar es porque ya estan en fase dos
                null //es nueva, no se ha regado antes
        );

        //agregamos el cultivo a la maceta
        pot.addCrop(newCrop);

        potRepository.save(pot); //guardamos la maceta con el nuevo cultivo agregado

        return newCrop.getId(); //devolvemos el id del cultivo agregado
    }
}
