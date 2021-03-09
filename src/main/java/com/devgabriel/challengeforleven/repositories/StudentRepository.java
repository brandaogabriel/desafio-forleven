package com.devgabriel.challengeforleven.repositories;

import com.devgabriel.challengeforleven.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
}
