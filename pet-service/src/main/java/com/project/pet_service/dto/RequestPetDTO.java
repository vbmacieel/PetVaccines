package com.project.pet_service.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestPetDTO {

    @NotNull
    @Max(50)
    private String name;

    @NotNull
    private String race;

    @NotNull
    private String birthDate;
}
