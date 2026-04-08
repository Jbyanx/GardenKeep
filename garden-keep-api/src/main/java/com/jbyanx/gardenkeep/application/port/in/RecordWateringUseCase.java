package com.jbyanx.gardenkeep.application.port.in;

import java.util.UUID;

public interface RecordWateringUseCase {
    // El caso de uso exacto que el usuario ejecutará desde la App Móvil
    void recordDeepWatering(UUID cropId);
}