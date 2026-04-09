package com.jbyanx.gardenkeep.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Crop {
    private final UUID id;
    private final CropType type;
    private GrowthStage currentStage;
    private LocalDateTime lastWateredAt;
    private static final LocalTime SAFE_SUN_TIME = LocalTime.of(15, 30);

    public boolean isSafeForSunBath(LocalTime currentTime) {
        return currentTime.isAfter(SAFE_SUN_TIME);
    }

    // Le pasamos la hora exacta del riego para no depender de LocalDateTime.now() dentro de la entidad (hace el testing más fácil)
    public void waterPlant(boolean isSoilDryAtSecondKnuckle, LocalDateTime wateringTime) {

        if (!isSoilDryAtSecondKnuckle) {
            throw new IllegalStateException("Peligro Botánico: La tierra aún está húmeda. Si riegas ahora, asfixiarás las raíces o pudrirás el bulbo.");
        }

        if (this.currentStage == GrowthStage.PHASE_1_SURFACE) {
            // Lógica de éxito: Mutamos el estado
            this.lastWateredAt = wateringTime;
            // (En un futuro, aquí podríamos retornar un objeto "SurfaceWateringAction")
            //o en esta fase va una cantidad de ml (ej 50ml)
        } else if (this.currentStage == GrowthStage.PHASE_2_DEEP) {
            // Lógica de éxito: Mutamos el estado
            this.lastWateredAt = wateringTime;
            // (En un futuro, aquí podríamos retornar un objeto "DeepWateringAction")
            //cantidad de agua (500ml) o algo así
        }
    }

    public void promoteToPhase2() {
        this.currentStage = GrowthStage.PHASE_2_DEEP;
    }
}