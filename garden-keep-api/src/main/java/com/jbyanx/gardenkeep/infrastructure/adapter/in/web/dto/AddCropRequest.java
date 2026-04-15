package com.jbyanx.gardenkeep.infrastructure.adapter.in.web.dto;

import com.jbyanx.gardenkeep.domain.model.CropType;

public record AddCropRequest(
        String type // Cambiamos CropType por String
) {}