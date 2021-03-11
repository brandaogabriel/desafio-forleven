package com.devgabriel.challengeforleven.tests.integration;

import com.devgabriel.challengeforleven.dtos.StudentDTO;
import com.devgabriel.challengeforleven.dtos.StudentInsertDTO;
import com.devgabriel.challengeforleven.dtos.StudentUpdateDTO;
import com.devgabriel.challengeforleven.services.StudentService;
import com.devgabriel.challengeforleven.services.exceptions.ResourceNotFoundException;
import com.devgabriel.challengeforleven.tests.factory.StudentFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SpringBootTest
@Transactional
class StudentServiceIT {

  @Autowired
  private StudentService service;

  private String existingEnrollment;
  private String nonExistingEnrollment;
  private long countTotalStudents;
  private StudentUpdateDTO existingStudent;
  private StudentInsertDTO newStudentInsertDTO;
  private String expectedStudentName;

  @BeforeEach
  void setUp() throws Exception {
    existingEnrollment = "1234";
    nonExistingEnrollment = "6789";
    countTotalStudents = 5L;
    existingStudent = StudentFactory.createStudentUpdateDTO();
    newStudentInsertDTO = StudentFactory.createStudentInsertDTO();
    expectedStudentName = "Jacob";
  }

  @Test
  void findAllShouldReturnStudents() {
    List<StudentInsertDTO> students = service.findAll();
    Assertions.assertFalse(students.isEmpty());
    Assertions.assertEquals(countTotalStudents, students.size());
  }

  @Test
  void findByEnrollmentShouldReturnStudentDTOWhenEnrollmentExists() {
    String expectedName = "Gabriel";
    StudentDTO studentDTO = service.findByEnrollment(existingEnrollment);
    Assertions.assertNotNull(studentDTO);
    Assertions.assertEquals(expectedName, studentDTO.getName());
  }

  @Test
  void findByEnrollmentShouldThrowsResourceNotFoundExceptionWhenEnrollmentDoesNotExists() {
    Assertions.assertThrows(ResourceNotFoundException.class, () -> service.findByEnrollment(nonExistingEnrollment));
  }

  @Test
  void insertShouldReturnStudentDTOWhenDataIsValid() {
    StudentInsertDTO dto = newStudentInsertDTO;
    StudentDTO studentDTO = service.insert(dto);
    Assertions.assertNotNull(studentDTO);
    Assertions.assertEquals(dto.getName(), studentDTO.getName());
  }

  @Test
  void updateShouldReturnStudentDTOWhenEnrollmentExists() {
    service.update(existingEnrollment, existingStudent);
    Assertions.assertNotNull(existingStudent);
    Assertions.assertEquals(expectedStudentName, existingStudent.getName());
  }

  @Test
  void updateShouldThrowsResourceNotFoundExceptionWhenEnrollmentDoesNotExists() {
    Assertions.assertThrows(ResourceNotFoundException.class, () -> service.update(nonExistingEnrollment, existingStudent));
  }

  @Test
  void deleteShouldDoNothingWhenEnrollmentExists() {
    Assertions.assertDoesNotThrow(() -> service.delete(existingEnrollment));
  }

  @Test
  void deleteShouldThrowsResourceNotFoundExceptionWhenEnrollmentDoesNotExists() {
    Assertions.assertThrows(ResourceNotFoundException.class, () -> service.delete(nonExistingEnrollment));
  }
}
