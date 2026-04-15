package com.jbyanx.gardenkeep.infrastructure.adapter.out.persistence.mapper;

import com.jbyanx.gardenkeep.domain.model.Crop;
import com.jbyanx.gardenkeep.infrastructure.adapter.out.persistence.entity.CropEntity;
import org.springframework.stereotype.Component;

@Component
public class CropPersistenceMapper {
    public Crop toDomain(CropEntity entity) {
        if(entity == null) return null;
        return new Crop(
                entity.getId(),
                entity.getType(),
                entity.getCurrentStage(),
                entity.getLastWateredAt()
        );
    }
    public CropEntity toEntity(Crop crop){
        if(crop == null) return null;
        return new CropEntity(
                crop.getId(),
                crop.getType(),
                crop.getCurrentStage(),
                crop.getLastWateredAt(),
                null // La relación con PotEntity se maneja en PotPersistenceMapper
        );
    }
}
