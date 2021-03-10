package com.devgabriel.challengeforleven.services;

import com.devgabriel.challengeforleven.dtos.StudentDTO;
import com.devgabriel.challengeforleven.entities.Student;
import com.devgabriel.challengeforleven.repositories.StudentRepository;
import com.devgabriel.challengeforleven.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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

  @Transactional
  public StudentDTO insert(StudentDTO studentDTO) {
    Student student = new Student();
    copyDtoToEntity(student, studentDTO);
    student = repository.save(student);
    return new StudentDTO(student);
  }

  private void copyDtoToEntity(Student student, StudentDTO studentDTO) {
    student.setEnrollment(studentDTO.getEnrollment());
    student.setName(studentDTO.getName());
    student.setLastName(studentDTO.getLastName());
  }

  @Transactional
  public StudentDTO update(String enrollment, StudentDTO dto) {
    try {
      Student student = repository.getOne(enrollment);
      copyDtoToEntity(student, dto);
      student = repository.save(student);
      return new StudentDTO(student);
    } catch (EntityNotFoundException e) {
      throw new ResourceNotFoundException("Student not found");
    }
  }

}
