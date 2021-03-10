package com.devgabriel.challengeforleven.repositories;

import com.devgabriel.challengeforleven.entities.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Phone, Long> {
}
