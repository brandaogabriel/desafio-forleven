package com.devgabriel.challengeforleven.services;

import com.devgabriel.challengeforleven.dtos.PhoneDTO;
import com.devgabriel.challengeforleven.dtos.StudentDTO;
import com.devgabriel.challengeforleven.dtos.StudentInsertDTO;
import com.devgabriel.challengeforleven.dtos.StudentUpdateDTO;
import com.devgabriel.challengeforleven.entities.Phone;
import com.devgabriel.challengeforleven.entities.Student;
import com.devgabriel.challengeforleven.repositories.PhoneRepository;
import com.devgabriel.challengeforleven.repositories.StudentRepository;
import com.devgabriel.challengeforleven.services.exceptions.DatabaseException;
import com.devgabriel.challengeforleven.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
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

  @Autowired
  private PhoneRepository phoneRepository;

  @Transactional(readOnly = true)
  public List<StudentInsertDTO> findAll() {
    List<Student> students = repository.findAll();
    return students.stream().map(StudentInsertDTO::new).collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public StudentDTO findByEnrollment(String enrollment) {
    Optional<Student> obj = repository.findById(enrollment);
    Student student = obj.orElseThrow(() -> new ResourceNotFoundException("Student not found"));
    return new StudentDTO(student);
  }

  @Transactional
  public StudentDTO insert(StudentInsertDTO dto) {
    Student student = new Student();
    student.setEnrollment(dto.getEnrollment());
    copyDtoToEntity(student, dto);
    student = repository.save(student);
    return new StudentDTO(student);
  }

  private void copyDtoToEntity(Student student, StudentDTO studentDTO) {
    removePhoneNumbers(student);

    student.setName(studentDTO.getName());
    student.setLastName(studentDTO.getLastName());

    for(PhoneDTO dto : studentDTO.getPhoneNumbers()) {
      Phone phone = new Phone(null, dto.getPhoneNumber());
      phone = phoneRepository.save(phone);
      student.getPhoneNumbers().add(phone);
    }
  }

  private void removePhoneNumbers(Student student) {
    for (Phone phone : student.getPhoneNumbers())
      phoneRepository.deleteById(phone.getId());

    student.getPhoneNumbers().clear();
  }

  @Transactional
  public StudentDTO update(String enrollment, StudentUpdateDTO dto) {
    try {
      Student student = repository.getOne(enrollment);
      copyDtoToEntity(student, dto);
      student = repository.save(student);
      return new StudentDTO(student);
    } catch (EntityNotFoundException e) {
      throw new ResourceNotFoundException("Student not found");
    }
  }

  public void delete(String enrollment) {
    try {
      repository.deleteById(enrollment);
    } catch (EmptyResultDataAccessException e) {
      throw new ResourceNotFoundException("Enrollment not found");
    } catch (DataIntegrityViolationException e) {
      throw new DatabaseException("Integrity violation");
    }
  }

}
