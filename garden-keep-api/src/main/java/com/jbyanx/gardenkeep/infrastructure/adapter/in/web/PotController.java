package com.jbyanx.gardenkeep.infrastructure.adapter.in.web;

import com.jbyanx.gardenkeep.application.port.in.AddCropToPotUseCase;
import com.jbyanx.gardenkeep.application.port.in.CreatePotUseCase;
import com.jbyanx.gardenkeep.domain.model.CropType;
import com.jbyanx.gardenkeep.infrastructure.adapter.in.web.dto.AddCropRequest;
import com.jbyanx.gardenkeep.infrastructure.adapter.in.web.dto.CreatePotRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/pots")
@RequiredArgsConstructor
public class PotController {
    private final CreatePotUseCase createPotUseCase;
    private final AddCropToPotUseCase addCropToPotUseCase;

    @PostMapping
    public ResponseEntity<UUID> createPot(@RequestBody CreatePotRequest request) {
        UUID potId = createPotUseCase.execute(request.name(), request.description());
        return new ResponseEntity<>(potId, HttpStatus.CREATED);
    }

    @PostMapping("/{potId}/crops")
    public ResponseEntity<UUID> addCropToPot(
            @PathVariable UUID potId,
            @RequestBody AddCropRequest request) {

        // Convertimos a Enum de forma segura y en mayúsculas
        CropType cropType = CropType.valueOf(request.type().toUpperCase());

        UUID cropId = addCropToPotUseCase.execute(potId, cropType);
        return new ResponseEntity<>(cropId, HttpStatus.CREATED);
    }
}