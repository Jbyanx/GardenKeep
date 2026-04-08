package com.jbyanx.gardenkeep.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Crop {
    private final UUID id;
    private final CropType type;
    private GrowthStage currentStage;

    // Regla de negocio: El baño de sol es seguro después de las 3:30 PM
    private static final LocalTime SAFE_SUN_TIME = LocalTime.of(15, 30);

    // Comportamiento 1: ¿Necesita baño de sol sin quemarse?
    public boolean isSafeForSunBath(LocalTime currentTime) {
        return currentTime.isAfter(SAFE_SUN_TIME);
    }

    // Comportamiento 2: Validar el tipo de riego
    public void performDeepWatering() {
        if (this.currentStage == GrowthStage.PHASE_1_SURFACE) {
            // ¡Aquí protegemos la regla de negocio!
            throw new IllegalStateException("No se puede hacer riego profundo en Fase 1. ¡Vas a pudrir el bulbo!");
        }
        // Lógica de riego profundo (ej. registrar la acción en una lista de eventos)
    }

    public void promoteToPhase2() {
        this.currentStage = GrowthStage.PHASE_2_DEEP;
    }

}