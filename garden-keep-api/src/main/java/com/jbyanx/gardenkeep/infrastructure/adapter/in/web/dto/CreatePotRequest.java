package com.jbyanx.gardenkeep.infrastructure.adapter.in.web.dto;

public record CreatePotRequest(
        String name,
        String description
) {}