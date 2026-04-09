package com.jbyanx.gardenkeep.application.service;

import com.jbyanx.gardenkeep.application.port.out.CropRepositoryPort;
import com.jbyanx.gardenkeep.domain.model.Crop;
import com.jbyanx.gardenkeep.domain.model.CropType;
import com.jbyanx.gardenkeep.domain.model.GrowthStage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CropWateringServiceTest {
    @Mock
    private CropRepositoryPort cropRepository;

    @InjectMocks
    private CropWateringService cropWateringService;

    //test 1: happy path
    @Test
    void shouldRecordWatheringAndSaveWhenCropExists(){
        //1. Arrange
        UUID cropId = UUID.randomUUID();
        //usamos una cebolla en fase 2 para que no lance excepcion
        Crop onion = new Crop(cropId, CropType.ONION, GrowthStage.PHASE_2_DEEP, null);

        //MOCKITO IN ACTION
        // Le decimos al mock: "Cuando alguien te pida buscar este ID, devuelve esta cebolla".
        when(cropRepository.findById(cropId)).thenReturn(Optional.of(onion));

        //2.Act se registra el riego profundo
        cropWateringService.waterCrop(cropId, true);

        //3. asserts verificamos que el repositorio falso
        //llama al metodo save una vez
        verify(cropRepository, times(1)).save(onion);
    }

    @Test
    void shouldThrowExceptionWhenCropDoesNotExist(){
        //Arrange
        UUID fakeId = UUID.randomUUID();

        //Mockito
        when(cropRepository.findById(fakeId)).thenReturn(Optional.empty());

        //act y assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> cropWateringService.waterCrop(fakeId, true));

        assertEquals("El cultivo con ID " + fakeId + " no existe.", exception.getMessage());

        //verificamos que nunca se haya intentado guardar nada en BD
        verify(cropRepository, never()).save(any());
    }
    @Test
    void shouldNotSaveWhenDomainRuleIsViolated(){
        UUID cropId = UUID.randomUUID();
        Crop onion = new Crop(cropId, CropType.ONION, GrowthStage.PHASE_1_SURFACE, null);

        when(cropRepository.findById(cropId)).thenReturn(Optional.of(onion));

        // 2. Act & Assert: Intentamos regar con tierra HÚMEDA (false)
        // El servicio debería propagar la excepción que lanza la entidad Crop
        assertThrows(IllegalStateException.class, () -> {
            cropWateringService.waterCrop(cropId, false);
        });

        // 3. Verificación de Seguridad
        // ¡SUPER IMPORTANTE! Verificamos que NUNCA se llamó al save
        // porque la regla botánica detuvo el proceso.
        verify(cropRepository, never()).save(any());
    }
}