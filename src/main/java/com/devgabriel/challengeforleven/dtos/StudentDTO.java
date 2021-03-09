package com.devgabriel.challengeforleven.dtos;

import com.devgabriel.challengeforleven.entities.Student;

import java.io.Serializable;

public class StudentDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  private String enrollment;
  private String name;
  private String lastName;

  public StudentDTO() {
  }

  public StudentDTO(String enrollment, String name, String lastName) {
    this.enrollment = enrollment;
    this.name = name;
    this.lastName = lastName;
  }

  public StudentDTO(Student entity) {
    enrollment = entity.getEnrollment();
    name = entity.getName();
    lastName = entity.getLastName();
  }

  public String getEnrollment() {
    return enrollment;
  }

  public void setEnrollment(String enrollment) {
    this.enrollment = enrollment;
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
