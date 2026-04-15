package com.jbyanx.gardenkeep.application.service;

import com.jbyanx.gardenkeep.application.port.in.GetPotUseCase;
import com.jbyanx.gardenkeep.application.port.out.PotRepositoryPort;
import com.jbyanx.gardenkeep.domain.exception.PotNotFoundException;
import com.jbyanx.gardenkeep.domain.model.Pot;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class GetPotService implements GetPotUseCase{
    private final PotRepositoryPort potRepository;

    @Override
    public Pot execute(UUID id) {
        return potRepository.findById(id)
                .orElseThrow(() -> new PotNotFoundException(id));
    }
}
