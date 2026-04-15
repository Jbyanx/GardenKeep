package com.jbyanx.gardenkeep.domain.model;

import com.jbyanx.gardenkeep.domain.exception.BotanicalRuleViolationException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Pot {
    private UUID id;
    private String name;
    private String description;
    private List<Crop> crops; //una maceta contiene varios cultivos

    // Mét odo para crear una maceta nueva con ID generado
    public static Pot createNew(String name, String description) {
        return new Pot(UUID.randomUUID(), name, description, new ArrayList<>());
    }

    public void addCrop(Crop crop) {
        if (this.crops.size() >= 5) throw new BotanicalRuleViolationException("Maceta llena, maximo 5 cultivos por maceta");
        this.crops.add(crop);
    }

    public void water(boolean isSoilDryAtSecondKnuckle, LocalDateTime wateringTime) {
        if (!isSoilDryAtSecondKnuckle) {
            throw new BotanicalRuleViolationException("La tierra de la maceta aún está húmeda.");
        }

        // La Maceta le da la orden a sus hijos, pasando los parámetros necesarios
        this.crops.forEach(crop -> crop.waterPlant(isSoilDryAtSecondKnuckle, wateringTime));
    }

     public boolean removeCrop(UUID cropId) {
        return this.crops.removeIf(crop -> crop.getId().equals(cropId));
    }
}
