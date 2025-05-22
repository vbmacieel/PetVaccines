package com.project.pet_service.dto;

import java.time.LocalDate;
import com.project.pet_service.entity.Pet;

import lombok.Data;

@Data
public class ResponsePetDTO {

    private Long id;
    private String name;
    private String race;
    private LocalDate birthDate;

    public ResponsePetDTO(Pet entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.race = entity.getRace();
        this.birthDate = entity.getBirthDate();
    }
}
