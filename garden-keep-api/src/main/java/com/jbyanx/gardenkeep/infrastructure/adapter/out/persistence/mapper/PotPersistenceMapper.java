package com.jbyanx.gardenkeep.infrastructure.adapter.out.persistence.mapper;

import com.jbyanx.gardenkeep.domain.model.Pot;
import com.jbyanx.gardenkeep.infrastructure.adapter.out.persistence.entity.CropEntity;
import com.jbyanx.gardenkeep.infrastructure.adapter.out.persistence.entity.PotEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PotPersistenceMapper {
    private final CropPersistenceMapper cropMapper; //debe ser final para que required args lo inyecte

    public Pot toDomain(PotEntity entity) {
        if (entity == null) return null;

        return new Pot(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getCrops() != null
                        ? entity.getCrops().stream()
                        .map(cropMapper::toDomain)
                        .toList()
                        : List.of()
        );
    }

    public PotEntity toEntity(Pot pot) {
        if (pot == null) return null;

        PotEntity potEntity = new PotEntity();
        potEntity.setId(pot.getId());
        potEntity.setName(pot.getName());
        potEntity.setDescription(pot.getDescription());

        if (pot.getCrops() != null) {
            List<CropEntity> cropEntities = pot.getCrops().stream()
                    .map(cropMapper::toEntity)
                    .toList();

            // ¡VITAL! Establecer la relación bidireccional para JPA
            cropEntities.forEach(crop -> crop.setPot(potEntity));
            potEntity.setCrops(cropEntities);
        }

        return potEntity;
    }
}
