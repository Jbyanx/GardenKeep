package com.jbyanx.gardenkeep.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CropTest {

    @Test
    void shouldThrowExceptionWhenSoilStillWet(){
        LocalDateTime now = LocalDateTime.now();
        //1. Arrange(preparar el escenario con una planta, su tipo y la fase 1 para que podamos recrear el escenario)
        Crop onion = new Crop(UUID.randomUUID(), CropType.ONION, GrowthStage.PHASE_1_SURFACE, null);

        // 2 & 3. Act & Assert (Actuar y verificar que lance el error esperado)
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
           onion.waterPlant(false, now);
        });

        assertEquals("Peligro Botánico: La tierra aún está húmeda. Si riegas ahora, asfixiarás las raíces o pudrirás el bulbo.", exception.getMessage());
    }

    @Test
    void shouldUpdateWateringDateWhenSoilIsDry(){
        //maceta lista para recibir agua
        Crop crop = new Crop(UUID.randomUUID(), CropType.GARLIC, GrowthStage.PHASE_1_SURFACE, null);
        //fecha del riego
        LocalDateTime wateringTime = LocalDateTime.of(2026, Month.APRIL, 9, 10, 0);

        // 2. Act: Regamos porque el nudillo salió seco (TRUE)
        crop.waterPlant(true, wateringTime);

        // 3. Assert: ¿La planta recordó el riego?
        assertEquals(wateringTime, crop.getLastWateredAt(), "La fecha de riego debe actualizarse");
    }

    @Test
    void shouldAllowSunBathAfter330PM(){
        //1. Arrange
        Crop garlic = new Crop(UUID.randomUUID(), CropType.GARLIC, GrowthStage.PHASE_1_SURFACE, null);
        LocalTime safeTime = LocalTime.of(16, 0); // 4:00 PM
        //2. Act
        boolean isSafe = garlic.isSafeForSunBath(safeTime);
        //3. Assert
        assertTrue(isSafe, "Debería ser seguro tomar el sol a las 4:00 PM");
    }

    @Test
    void shouldNotAllowSunBathAtNoon() {
        // 1. Arrange
        Crop onion = new Crop(UUID.randomUUID(), CropType.ONION, GrowthStage.PHASE_1_SURFACE, null);
        LocalTime dangerousTime = LocalTime.of(12, 0); // 12:00 PM (Mediodía)

        // 2. Act
        boolean isSafe = onion.isSafeForSunBath(dangerousTime);

        // 3. Assert
        assertFalse(isSafe, "No debería ser seguro tomar el sol al mediodía con techo de zinc");
    }
}