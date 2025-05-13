package com.project.pet_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.pet_service.entity.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

}
