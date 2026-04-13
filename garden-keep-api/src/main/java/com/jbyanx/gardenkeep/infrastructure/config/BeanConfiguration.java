package com.jbyanx.gardenkeep.infrastructure.config;

import com.jbyanx.gardenkeep.application.port.out.CropRepositoryPort;
import com.jbyanx.gardenkeep.application.service.CropWateringService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    //aca decidimos que adaptador de base de datos usamos, en este caso el de postgres
    @Bean
    public CropWateringService cropWateringService(@Qualifier("postgresCropRepositoryAdapter") CropRepositoryPort cropRepositoryPort) {
        // Aquí "inyectamos" manualmente las dependencias.
        // Spring buscará una implementación de CropRepositoryPort.
        return new CropWateringService(cropRepositoryPort);
    }
}