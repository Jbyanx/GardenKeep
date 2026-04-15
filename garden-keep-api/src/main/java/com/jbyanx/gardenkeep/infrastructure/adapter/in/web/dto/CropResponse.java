package com.jbyanx.gardenkeep.infrastructure.adapter.in.web.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CropResponse(
        UUID id,
        String type,
        String currentStage,
        LocalDateTime lastWateredAt
) {}