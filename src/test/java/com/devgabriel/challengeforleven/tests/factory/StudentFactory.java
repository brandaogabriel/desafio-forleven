package com.devgabriel.challengeforleven.tests.factory;

import com.devgabriel.challengeforleven.dtos.StudentDTO;
import com.devgabriel.challengeforleven.dtos.StudentInsertDTO;
import com.devgabriel.challengeforleven.dtos.StudentUpdateDTO;
import com.devgabriel.challengeforleven.entities.Phone;
import com.devgabriel.challengeforleven.entities.Student;

public class StudentFactory {

  public static Student createStudent() {
    Student student = new Student("5544", "Jacob", "Woods");
    student.getPhoneNumbers().add(new Phone(null, "99988-7744"));
    return student;
  }

  public static StudentDTO createStudentDTO() {
    return new StudentDTO(createStudent());
  }

  public static StudentUpdateDTO createStudentUpdateDTO() {
    StudentUpdateDTO dto = new StudentUpdateDTO();
    Student student = createStudent();
    dto.setName(student.getName());
    dto.setLastName(student.getLastName());
    return dto;
  }

  public static StudentInsertDTO createStudentInsertDTO() {
    Student student = createStudent();
    return new StudentInsertDTO(student);
  }
}
