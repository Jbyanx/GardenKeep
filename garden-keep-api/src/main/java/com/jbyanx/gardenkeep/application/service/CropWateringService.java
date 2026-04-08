package com.jbyanx.gardenkeep.application.service;

import com.jbyanx.gardenkeep.application.port.in.RecordWateringUseCase;
import com.jbyanx.gardenkeep.application.port.out.CropRepositoryPort;
import com.jbyanx.gardenkeep.domain.model.Crop;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class CropWateringService implements RecordWateringUseCase {
    private final CropRepositoryPort cropRepository;

    @Override
    public void recordDeepWatering(UUID cropId) {
        //buscamos el cultivo
        Crop crop = cropRepository.findById(cropId)
                .orElseThrow(() -> new IllegalArgumentException("El cultivo con ID " + cropId + " no existe."));

        //ejecutamos logica DEL DOMINIO
        crop.performDeepWatering();

        //guardamos el nuevo estado
        cropRepository.save(crop);
    }
}
