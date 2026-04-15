package com.jbyanx.gardenkeep.infrastructure.config;

import com.jbyanx.gardenkeep.application.port.in.CreatePotUseCase;
import com.jbyanx.gardenkeep.application.port.out.CropRepositoryPort;
import com.jbyanx.gardenkeep.application.port.out.PotRepositoryPort;
import com.jbyanx.gardenkeep.application.service.CropWateringService;
import com.jbyanx.gardenkeep.application.service.CreatePotService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

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