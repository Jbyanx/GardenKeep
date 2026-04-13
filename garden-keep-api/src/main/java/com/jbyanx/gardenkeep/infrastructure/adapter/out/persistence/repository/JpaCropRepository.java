package com.jbyanx.gardenkeep.infrastructure.adapter.out.persistence.repository;

import com.jbyanx.gardenkeep.infrastructure.adapter.out.persistence.entity.CropEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaCropRepository extends JpaRepository<CropEntity, UUID> {
}
