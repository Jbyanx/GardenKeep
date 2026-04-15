package com.jbyanx.gardenkeep.infrastructure.config;

import com.jbyanx.gardenkeep.application.port.in.AddCropToPotUseCase;
import com.jbyanx.gardenkeep.application.port.in.CreatePotUseCase;
import com.jbyanx.gardenkeep.application.port.in.GetPotUseCase;
import com.jbyanx.gardenkeep.application.port.in.WaterPotUseCase;
import com.jbyanx.gardenkeep.application.port.out.CropRepositoryPort;
import com.jbyanx.gardenkeep.application.port.out.PotRepositoryPort;
import com.jbyanx.gardenkeep.application.service.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public WaterPotUseCase waterPotUseCase(PotRepositoryPort potRepositoryPort) {
        return new WaterPotService(potRepositoryPort);
    }

    @Bean
    public GetPotUseCase getPotUseCase(PotRepositoryPort potRepositoryPort) {
        return new GetPotService(potRepositoryPort);
    }

    @Bean
    public AddCropToPotUseCase addCropToPotUseCase(PotRepositoryPort potRepositoryPort) {
        return new AddCropToPotService(potRepositoryPort);
    }

    //aca decidimos que adaptador de base de datos usamos, en este caso el de postgres
    @Bean
    public CropWateringService cropWateringService(@Qualifier("jpaCropRepositoryAdapter") CropRepositoryPort cropRepositoryPort) {
        // Aquí "inyectamos" manualmente las dependencias.
        // Spring buscará una implementación de CropRepositoryPort.
        return new CropWateringService(cropRepositoryPort);
    }

    @Bean
    public CreatePotUseCase createPotUseCase(PotRepositoryPort potRepositoryPort) {
        return new CreatePotService(potRepositoryPort);
    }
}