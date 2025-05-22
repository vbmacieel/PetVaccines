package com.project.pet_service.service;

import java.security.InvalidParameterException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.pet_service.dto.RequestPetDTO;
import com.project.pet_service.dto.ResponsePetDTO;
import com.project.pet_service.entity.Pet;
import com.project.pet_service.repository.PetRepository;
import com.project.pet_service.util.ClassMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository repository;

    public List<ResponsePetDTO> findAll() {
        return repository.findAll().stream().map(ClassMapper::toDto).toList();
    }

    public ResponsePetDTO save(RequestPetDTO dto) {
        Pet pet = ClassMapper.toEntity(dto);
        repository.save(pet);

        return ClassMapper.toDto(pet);
    }

    public ResponsePetDTO findById(Long id) {
        Pet pet = findPetOrElseThrow(id);
        return ClassMapper.toDto(pet);
    }

    public void delete(Long id) {
        Pet pet = findPetOrElseThrow(id);
        repository.delete(pet);
    }

    public ResponsePetDTO update(RequestPetDTO dto, Long id) {
        Pet pet = findPetOrElseThrow(id);
        ClassMapper.updateEntityFromDto(dto, pet);

        repository.save(pet);
        return ClassMapper.toDto(pet);
    }

    private Pet findPetOrElseThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new InvalidParameterException("No pet found with this id!!"));
    }
}
