package com.devgabriel.challengeforleven.services.exceptions.validation;

import com.devgabriel.challengeforleven.dtos.StudentDTO;
import com.devgabriel.challengeforleven.dtos.StudentInsertDTO;
import com.devgabriel.challengeforleven.entities.Student;
import com.devgabriel.challengeforleven.repositories.StudentRepository;
import com.devgabriel.challengeforleven.resources.exceptions.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentInsertValidator implements ConstraintValidator<StudentInsertValid, StudentInsertDTO> {

  @Autowired
  private StudentRepository repository;

  @Override
  public void initialize(StudentInsertValid ann) {
  }

  @Override
  public boolean isValid(StudentInsertDTO dto, ConstraintValidatorContext context) {
    List<FieldMessage> list = new ArrayList<>();

    Optional<Student> obj = repository.findById(dto.getEnrollment());
    if (obj.isPresent())
      list.add(new FieldMessage("enrollment", "Enrollment already exists"));

    for (FieldMessage e : list) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
              .addConstraintViolation();
    }
    return list.isEmpty();
  }
}
