package com.jbyanx.gardenkeep.infrastructure.adapter.out.persistence.entity;

import com.jbyanx.gardenkeep.domain.model.CropType;
import com.jbyanx.gardenkeep.domain.model.GrowthStage;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "crops")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CropEntity {
    @Id
    // Quitamos elGeneratedValue(strategy = GenerationType.UUID)
    // porque el ID ya viene generado desde nuestro Dominio (UUID.randomUUID())
    private UUID id;

    @Enumerated(value = EnumType.STRING)
    private CropType type;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "current_stage")
    private GrowthStage currentStage;

    @Column(name = "last_watered_at")
    private LocalDateTime lastWateredAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pot_id", nullable = false)
    private PotEntity pot;
}
