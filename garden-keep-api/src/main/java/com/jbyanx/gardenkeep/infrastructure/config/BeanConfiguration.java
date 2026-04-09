package com.jbyanx.gardenkeep.infrastructure.config;

import com.jbyanx.gardenkeep.application.port.out.CropRepositoryPort;
import com.jbyanx.gardenkeep.application.service.CropWateringService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public CropWateringService cropWateringService(CropRepositoryPort cropRepositoryPort) {
        // Aquí "inyectamos" manualmente las dependencias.
        // Spring buscará una implementación de CropRepositoryPort (que haremos luego).
        return new CropWateringService(cropRepositoryPort);
    }
}