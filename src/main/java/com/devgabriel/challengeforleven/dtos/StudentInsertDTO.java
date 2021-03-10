package com.devgabriel.challengeforleven.dtos;

import com.devgabriel.challengeforleven.entities.Student;
import com.devgabriel.challengeforleven.services.exceptions.validation.StudentInsertValid;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@StudentInsertValid
public class StudentInsertDTO extends StudentDTO {
  private static final long serialVersionUID = 1L;

  @NotBlank(message = "Enrollment is required")
  @Size(min = 4, message = "Enrollment field must have at least 4 characters")
  private String enrollment;

  public StudentInsertDTO() {
    super();
  }

  public StudentInsertDTO(Student entity) {
    enrollment = entity.getEnrollment();
    super.setName(entity.getName());
    super.setLastName(entity.getLastName());
  }

  public String getEnrollment() {
    return enrollment;
  }

  public void setEnrollment(String enrollment) {
    this.enrollment = enrollment;
  }
}
