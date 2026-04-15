package com.jbyanx.gardenkeep.infrastructure.adapter.in.web.mapper;

import com.jbyanx.gardenkeep.domain.model.Crop;
import com.jbyanx.gardenkeep.domain.model.Pot;
import com.jbyanx.gardenkeep.infrastructure.adapter.in.web.dto.CropResponse;
import com.jbyanx.gardenkeep.infrastructure.adapter.in.web.dto.PotResponse;
import org.springframework.stereotype.Component;

@Component
public class PotWebMapper {

    public PotResponse toResponse(Pot pot) {
        return new PotResponse(
                pot.getId(),
                pot.getName(),
                pot.getDescription(),
                pot.getCrops().stream()
                        .map(this::toCropResponse)
                        .toList()
        );
    }

    private CropResponse toCropResponse(Crop crop) {
        return new CropResponse(
                crop.getId(),
                crop.getType().name(),
                crop.getCurrentStage().name(),
                crop.getLastWateredAt()
        );
    }
}
