package com.jbyanx.gardenkeep.infrastructure.adapter.in.web.dto;

import java.util.List;
import java.util.UUID;

public record PotResponse(
        UUID id,
        String name,
        String description,
        List<CropResponse> crops
) { }