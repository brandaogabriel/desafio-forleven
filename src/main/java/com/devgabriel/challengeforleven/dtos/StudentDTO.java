package com.devgabriel.challengeforleven.dtos;

import com.devgabriel.challengeforleven.entities.Student;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class StudentDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  @NotBlank(message = "Name field is required")
  @Size(min = 4, message = "Name field must have at least 4 characters")
  private String name;

  @NotBlank(message = "Last name field is required")
  @Size(min = 4, message = "Last name field must have ate least 4 characters")
  private String lastName;

  public StudentDTO() {
  }

  public StudentDTO(String name, String lastName) {
    this.name = name;
    this.lastName = lastName;
  }

  public StudentDTO(Student entity) {
    name = entity.getName();
    lastName = entity.getLastName();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
}
