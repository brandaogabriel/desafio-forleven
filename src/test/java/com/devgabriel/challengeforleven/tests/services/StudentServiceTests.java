package com.devgabriel.challengeforleven.tests.services;

import com.devgabriel.challengeforleven.dtos.StudentDTO;
import com.devgabriel.challengeforleven.dtos.StudentInsertDTO;
import com.devgabriel.challengeforleven.dtos.StudentUpdateDTO;
import com.devgabriel.challengeforleven.entities.Phone;
import com.devgabriel.challengeforleven.entities.Student;
import com.devgabriel.challengeforleven.repositories.PhoneRepository;
import com.devgabriel.challengeforleven.repositories.StudentRepository;
import com.devgabriel.challengeforleven.services.StudentService;
import com.devgabriel.challengeforleven.services.exceptions.DatabaseException;
import com.devgabriel.challengeforleven.services.exceptions.ResourceNotFoundException;
import com.devgabriel.challengeforleven.tests.factory.PhoneFactory;
import com.devgabriel.challengeforleven.tests.factory.StudentFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class StudentServiceTests {

  @InjectMocks
  private StudentService service;

  @Mock
  private StudentRepository repository;

  @Mock
  private PhoneRepository phoneRepository;

  private String existingEnrollment;
  private String nonExistingEnrollment;
  private String dependingEnrollment;
  private Student student;
  private StudentInsertDTO newStudentInsertDTO;

  @BeforeEach
  void setUp() throws Exception {
    existingEnrollment = "1234";
    nonExistingEnrollment = "6789";
    dependingEnrollment = "3456";
    student = StudentFactory.createStudent();
    newStudentInsertDTO = StudentFactory.createStudentInsertDTO();
    Phone phone = PhoneFactory.createPhone();

    Mockito.when(repository.findAll()).thenReturn(List.of(student));
    Mockito.when(repository.findById(existingEnrollment)).thenReturn(Optional.of(student));
    Mockito.when(repository.findById(nonExistingEnrollment)).thenReturn(Optional.empty());
    Mockito.when(repository.getOne(existingEnrollment)).thenReturn(student);
    Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(student);
    Mockito.when(phoneRepository.save(ArgumentMatchers.any())).thenReturn(phone);

    Mockito.doThrow(EntityNotFoundException.class).when(repository).getOne(nonExistingEnrollment);
    Mockito.doNothing().when(repository).deleteById(existingEnrollment);
    Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingEnrollment);
    Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependingEnrollment);
  }

  @Test
  void findAllShouldReturnListOfStudents() {
    List<StudentInsertDTO> students = service.findAll();
    Assertions.assertNotNull(students);
    Assertions.assertEquals(1, students.size());
    Mockito.verify(repository, Mockito.times(1)).findAll();
  }

  @Test
  void findByEnrollmentShouldReturnStudentDTOWhenEnrollmentExists() {
    StudentDTO dto = service.findByEnrollment(existingEnrollment);
    Assertions.assertNotNull(dto);
    Assertions.assertEquals(dto.getName(), student.getName());
  }

  @Test
  void findByEnrollmentShouldThrowResourceNotFoundExceptionWhenEnrollmentDoesNotExists() {
    Assertions.assertThrows(ResourceNotFoundException.class, () -> service.findByEnrollment(nonExistingEnrollment));
  }

  @Test
  void insertShouldReturnStudentDTOWhenValidData() {
    StudentDTO dto = service.insert(newStudentInsertDTO);
    Assertions.assertNotNull(dto);
    Assertions.assertEquals(dto.getName(), student.getName());
  }

  @Test
  void updateShouldReturnStudentDTOWhenEnrollmentExists() {
    StudentUpdateDTO dto = new StudentUpdateDTO();
    StudentDTO studentDTO = service.update(existingEnrollment, dto);
    Assertions.assertNotNull(studentDTO);
    Assertions.assertEquals(studentDTO.getName(), student.getName());
  }

  @Test
  void updateShouldThrowResourceNotFoundExceptionWhenEnrollmentDoesNotExists() {
    StudentUpdateDTO dto = new StudentUpdateDTO();
    Assertions.assertThrows(ResourceNotFoundException.class, () -> service.update(nonExistingEnrollment, dto));
    Mockito.verify(repository, Mockito.times(1)).getOne(nonExistingEnrollment);
  }

  @Test
  void deleteShouldDoNothingWhenEnrollmentExists() {
    Assertions.assertDoesNotThrow(() -> service.delete(existingEnrollment));
    Mockito.verify(repository, Mockito.times(1)).deleteById(existingEnrollment);
  }

  @Test
  void deleteShouldThrowResourceNotFoundExceptionWhenEnrollmentDoesNotExists() {
    Assertions.assertThrows(ResourceNotFoundException.class, () -> service.delete(nonExistingEnrollment));
    Mockito.verify(repository, Mockito.times(1)).deleteById(nonExistingEnrollment);
  }

  @Test
  void deleteShouldThrowDatabaseExceptionWhenEnrollmentIsDependent() {
    Assertions.assertThrows(DatabaseException.class, () -> service.delete(dependingEnrollment));
    Mockito.verify(repository, Mockito.times(1)).deleteById(dependingEnrollment);
  }
}
