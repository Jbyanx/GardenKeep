package com.jbyanx.gardenkeep.infrastructure.adapter.in.web;

import com.jbyanx.gardenkeep.application.port.in.RecordWateringUseCase;
import com.jbyanx.gardenkeep.infrastructure.adapter.in.web.dto.WateringRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/crops")
@RequiredArgsConstructor
public class CropController {
    private final RecordWateringUseCase wateringUseCase;

    @PostMapping("/{id}/water")
    public ResponseEntity<String> waterCrop(
            @RequestBody WateringRequest request,
            @PathVariable UUID id) {

        // Traducimos del mundo Web al mundo Aplicación
        wateringUseCase.waterCrop(id, request.soilDryAtSecondKnuckle());

        return ResponseEntity.ok("Riego procesado con éxito para el cultivo: " + id);
    }
}
