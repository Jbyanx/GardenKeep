package com.jbyanx.gardenkeep.infrastructure.adapter.in.web.dto;

import java.io.Serializable;
import java.util.UUID;

/***
 * peticion de regar la planta
 */
public record WateringRequest(
        boolean soilDryAtSecondKnuckle
) implements Serializable {
}
