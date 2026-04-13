package com.jbyanx.gardenkeep.application.service;

import com.jbyanx.gardenkeep.application.port.in.RecordWateringUseCase;
import com.jbyanx.gardenkeep.application.port.out.CropRepositoryPort;
import com.jbyanx.gardenkeep.domain.exception.CropNotFoundException;
import com.jbyanx.gardenkeep.domain.model.Crop;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j; // <-- ¡El Logger profesional!

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j // Lombok crea una variable 'log' automáticamente
@RequiredArgsConstructor
public class CropWateringService implements RecordWateringUseCase {

    private final CropRepositoryPort cropRepository;

    @Override
    public void waterCrop(UUID cropId, boolean isSoilDryAtSecondKnuckle) {

        log.info("Iniciando proceso de riego para el cultivo con ID: {}", cropId);

        Crop crop = cropRepository.findById(cropId)
                .orElseThrow(() -> {
                    log.error("Fallo al regar: No se encontró el cultivo ID {}", cropId);
                    return new CropNotFoundException("El cultivo con ID " + cropId + " no existe.");
                });

        // La planta hace su validación biológica y actualiza su fecha interna
        crop.waterPlant(isSoilDryAtSecondKnuckle, LocalDateTime.now());

        // Guardamos el nuevo estado (con la fecha actualizada) en la BD
        cropRepository.save(crop);

        log.info("Riego registrado exitosamente en base de datos para el cultivo {}. Fase actual: {}",
                cropId, crop.getCurrentStage());
    }
}