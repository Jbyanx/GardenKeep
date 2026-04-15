package com.jbyanx.gardenkeep.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "pots")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PotEntity {
    @Id
    private UUID id;
    private String name;
    private String description;

    @OneToMany(
            mappedBy = "pot",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<CropEntity> crops;
}
