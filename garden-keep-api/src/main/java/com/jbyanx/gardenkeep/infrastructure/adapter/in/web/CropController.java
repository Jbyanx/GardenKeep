package com.jbyanx.gardenkeep.infrastructure.adapter.in.web;

import com.jbyanx.gardenkeep.application.port.in.RecordWateringUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/crops")
@RequiredArgsConstructor
public class CropController {
    private final RecordWateringUseCase wateringUseCase;

    //todo listado de plantas del usuario
}
