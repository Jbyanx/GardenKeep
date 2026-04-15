package com.jbyanx.gardenkeep.application.port.in;

import java.util.UUID;

public interface WaterPotUseCase {
    void execute(UUID potId, boolean isSoilDryAtSecondKnuckle);
}