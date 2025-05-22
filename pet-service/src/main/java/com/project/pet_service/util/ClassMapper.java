package com.project.pet_service.util;

import com.project.pet_service.dto.RequestPetDTO;
import com.project.pet_service.dto.ResponsePetDTO;
import com.project.pet_service.entity.Pet;

public final class ClassMapper {

    private ClassMapper() {
        throw new UnsupportedOperationException("This is an utility class and cannot be instantiated!");
    }

    public static ResponsePetDTO toDto(Pet entity) {
        return new ResponsePetDTO(entity);
    }

    public static Pet toEntity(RequestPetDTO dto) {
        return new Pet(dto.getName(), dto.getRace(), FormatDateUtil.format(dto.getBirthDate()));
    }
}
