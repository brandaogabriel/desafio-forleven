package com.devgabriel.challengeforleven.resources;

import com.devgabriel.challengeforleven.dtos.StudentDTO;
import com.devgabriel.challengeforleven.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/students")
public class StudentResource {

  @Autowired
  private StudentService service;

  @GetMapping
  public ResponseEntity<List<StudentDTO>> findAll() {
    List<StudentDTO> studentsDto = service.findAll();
    return ResponseEntity.ok().body(studentsDto);
  }

  @GetMapping(value = "/{enrollment}")
  public ResponseEntity<StudentDTO> findByEnrollment(@PathVariable String enrollment) {
    StudentDTO studentDto = service.findByEnrollment(enrollment);
    return ResponseEntity.ok().body(studentDto);
  }
}
