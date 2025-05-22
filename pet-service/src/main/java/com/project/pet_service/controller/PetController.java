package com.project.pet_service.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.pet_service.dto.RequestPetDTO;
import com.project.pet_service.dto.ResponsePetDTO;
import com.project.pet_service.service.PetService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetService service;

    @GetMapping
    public ResponseEntity<List<ResponsePetDTO>> findAllPets() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @PostMapping
    public ResponseEntity<ResponsePetDTO> savePet(@Valid @RequestBody RequestPetDTO dto,
            UriComponentsBuilder uriBuilder) {
        ResponsePetDTO responsePet = service.save(dto);
        URI location = uriBuilder.path("pet/{id}")
                .buildAndExpand(responsePet.getId())
                .toUri();
        return ResponseEntity.created(location).body(responsePet);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePetDTO> findPetById(@PathVariable Long id) {
        ResponsePetDTO responsePet = service.findById(id);
        return ResponseEntity.ok().body(responsePet);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponsePetDTO> updatePet(@PathVariable Long id, @Valid @RequestBody RequestPetDTO dto) {
        ResponsePetDTO responsePet = service.update(dto, id);
        return ResponseEntity.ok().body(responsePet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
