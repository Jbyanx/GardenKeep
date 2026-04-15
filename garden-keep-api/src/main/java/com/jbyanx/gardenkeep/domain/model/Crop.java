package com.jbyanx.gardenkeep.domain.model;

import com.jbyanx.gardenkeep.domain.exception.BotanicalRuleViolationException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Duration;
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
            throw new BotanicalRuleViolationException("Peligro Botánico: La tierra aún está húmeda. Si riegas ahora, asfixiarás las raíces o pudrirás el bulbo.");
        }

        // 🛑 NUEVA REGLA: Prevención de Riego Infinito (Cooldown)
        if (this.lastWateredAt != null) {
            long minutesSinceLastWatering = Duration.between(this.lastWateredAt, wateringTime).toMinutes();

            // Para el MVP y pruebas rápidas, pondremos 5 minutos.
            // En producción, esto debería ser al menos 12 o 24 horas (ej. toHours() < 12)
            if (minutesSinceLastWatering < 5) {
                throw new BotanicalRuleViolationException(
                        "Peligro Botánico: El cultivo ya fue regado hace " + minutesSinceLastWatering +
                                " minutos. Espera a que el sustrato drene correctamente."
                );
            }
        }

        if (this.currentStage == GrowthStage.PHASE_1_SURFACE) {
            this.lastWateredAt = wateringTime;
        } else if (this.currentStage == GrowthStage.PHASE_2_DEEP) {
            this.lastWateredAt = wateringTime;
        }
    }

    public void promoteToPhase2() {
        this.currentStage = GrowthStage.PHASE_2_DEEP;
    }
}