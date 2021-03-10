package com.devgabriel.challengeforleven.resources;

import com.devgabriel.challengeforleven.dtos.StudentDTO;
import com.devgabriel.challengeforleven.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

  @PostMapping
  public ResponseEntity<StudentDTO> insert(@RequestBody StudentDTO dto) {
    dto = service.insert(dto);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{enrollment}")
            .buildAndExpand(dto.getEnrollment()).toUri();
    return ResponseEntity.created(uri).body(dto);
  }

  @PutMapping(value = "/{enrollment}")
  public ResponseEntity<StudentDTO> update(@PathVariable String enrollment, @RequestBody StudentDTO dto) {
    dto = service.update(enrollment, dto);
    return ResponseEntity.ok().body(dto);
  }
}
