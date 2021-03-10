package com.devgabriel.challengeforleven.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tb_student")
public class Student implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  private String enrollment;

  private String name;
  private String lastName;

  public Student() {
  }

  public Student(String enrollment, String name, String lastName) {
    this.enrollment = enrollment;
    this.name = name;
    this.lastName = lastName;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Student student = (Student) o;
    return Objects.equals(enrollment, student.enrollment);
  }

  @Override
  public int hashCode() {
    return Objects.hash(enrollment);
  }
}
