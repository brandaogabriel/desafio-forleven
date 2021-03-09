package com.devgabriel.challengeforleven.services;

import com.devgabriel.challengeforleven.dtos.StudentDTO;
import com.devgabriel.challengeforleven.entities.Student;
import com.devgabriel.challengeforleven.repositories.StudentRepository;
import com.devgabriel.challengeforleven.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

  @Autowired
  private StudentRepository repository;

  @Transactional(readOnly = true)
  public List<StudentDTO> findAll() {
    List<Student> students = repository.findAll();
    return students.stream().map(student -> new StudentDTO(student)).collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public StudentDTO findByEnrollment(String enrollment) {
    Optional<Student> obj = repository.findById(enrollment);
    Student student = obj.orElseThrow(() -> new ResourceNotFoundException("Student not found"));
    return new StudentDTO(student);
  }
}
