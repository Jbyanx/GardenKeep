package com.jbyanx.gardenkeep.application.port.in;

import com.jbyanx.gardenkeep.domain.model.Pot;

import java.util.UUID;

public interface GetPotUseCase {
    Pot execute(UUID id);
}