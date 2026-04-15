package com.jbyanx.gardenkeep.application.port.in;

import com.jbyanx.gardenkeep.domain.model.CropType;

import java.util.UUID;

public interface AddCropToPotUseCase {
    /**
     * para ejecutarse "agregar planta a la maceta" se debe saber a que maceta y que tipo de planta se va a agregar, por eso los parametros son el id de la maceta y el tipo de planta, y devuelve el id del cultivo creado
     * @param potId maceta a la cual se agrega el cultivo
     * @param type tipo de planta que se agrega
     * @return id del cultivo agregado a la maceta
     */

    UUID execute(UUID potId, CropType type);
}
