package com.jbyanx.gardenkeep.infrastructure.adapter.out.persistence.repository;

import com.jbyanx.gardenkeep.infrastructure.adapter.out.persistence.entity.PotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaPotRepository extends JpaRepository<PotEntity, UUID> {
}
