package com.jbyanx.gardenkeep.application.port.in;

import java.util.UUID;

public interface CreatePotUseCase {
    UUID execute(String name, String description); //recibe los datos y devuelve el Id creado
}
