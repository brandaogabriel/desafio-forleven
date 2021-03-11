package com.devgabriel.challengeforleven.tests.web;

import com.devgabriel.challengeforleven.dtos.StudentDTO;
import com.devgabriel.challengeforleven.dtos.StudentInsertDTO;
import com.devgabriel.challengeforleven.dtos.StudentUpdateDTO;
import com.devgabriel.challengeforleven.services.StudentService;
import com.devgabriel.challengeforleven.services.exceptions.ResourceNotFoundException;
import com.devgabriel.challengeforleven.tests.factory.StudentFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StudentResourceTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private StudentService service;

  private String existingEnrollment;
  private String nonExistingEnrollment;
  private StudentDTO studentDTO;
  private StudentUpdateDTO existingStudent;
  private StudentInsertDTO newStudentInsertDTO;

  @BeforeEach
  void setUp() throws Exception {
    existingEnrollment = "1234";
    nonExistingEnrollment = "6789";
    studentDTO = StudentFactory.createStudentDTO();
    existingStudent = StudentFactory.createStudentUpdateDTO();
    newStudentInsertDTO = StudentFactory.createStudentInsertDTO();

    Mockito.when(service.findAll()).thenReturn(List.of(newStudentInsertDTO));
    Mockito.when(service.findByEnrollment(existingEnrollment)).thenReturn(studentDTO);
    Mockito.when(service.findByEnrollment(nonExistingEnrollment)).thenThrow(ResourceNotFoundException.class);
    Mockito.when(service.insert(any())).thenReturn(studentDTO);
    Mockito.when(service.update(eq(existingEnrollment), any())).thenReturn(existingStudent);
    Mockito.when(service.update(eq(nonExistingEnrollment), any())).thenThrow(ResourceNotFoundException.class);

    Mockito.doNothing().when(service).delete(existingEnrollment);
    Mockito.doThrow(ResourceNotFoundException.class).when(service).delete(nonExistingEnrollment);
  }

  @Test
  void findAllShouldReturnStudents() throws Exception {
    mockMvc.perform(get("/students")
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andDo(print())
            .andExpect(status().isOk());
  }

  @Test
  void findByEnrollmentShouldReturnStudentDTOWhenEnrollmentExists() throws Exception {
    mockMvc.perform(get("/students/{enrollment}", existingEnrollment)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").exists())
            .andExpect(jsonPath("$.name").value(studentDTO.getName()));
  }

  @Test
  void findByEnrollmentShouldReturnNotFoundWhenEnrollmentDoesNotExists() throws Exception {
    mockMvc.perform(get("/students/{enrollment}", nonExistingEnrollment)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNotFound());
  }

  @Test
  void insertShouldReturnCreatedStudentDTOWhenDataIsValid() throws Exception {
    String jsonBody = objectMapper.writeValueAsString(newStudentInsertDTO);

    mockMvc.perform(post("/students")
            .content(jsonBody)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(jsonPath("$.name").exists())
            .andExpect(status().isCreated());
  }

  @Test
  void insertShouldReturnUnprocessableEntityWhenEnrollmentAlreadyExists() throws Exception {
    newStudentInsertDTO.setEnrollment("1234");
    String jsonBody = objectMapper.writeValueAsString(newStudentInsertDTO);

    mockMvc.perform(post("/students")
            .content(jsonBody)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isUnprocessableEntity());
  }

  @Test
  void insertShouldReturnUnprocessableEntityWhenEnrollmentIsInvalid() throws Exception {
    newStudentInsertDTO.setEnrollment("");
    String jsonBody = objectMapper.writeValueAsString(newStudentInsertDTO);

    mockMvc.perform(post("/students")
            .content(jsonBody)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isUnprocessableEntity());
  }

  @Test
  void insertShouldReturnUnprocessableEntityWhenNameIsInvalid() throws Exception {
    newStudentInsertDTO.setName("");
    String jsonBody = objectMapper.writeValueAsString(newStudentInsertDTO);

    mockMvc.perform(post("/students")
            .content(jsonBody)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isUnprocessableEntity());
  }

  @Test
  void insertShouldReturnUnprocessableEntityWhenNameSizeIsInValid() throws Exception {
    newStudentInsertDTO.setName("gab");
    String jsonBody = objectMapper.writeValueAsString(newStudentInsertDTO);

    mockMvc.perform(post("/students")
            .content(jsonBody)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isUnprocessableEntity());
  }

  @Test
  void insertShouldReturnUnprocessableEntityWhenLastNameIsInValid() throws Exception {
    newStudentInsertDTO.setLastName("");
    String jsonBody = objectMapper.writeValueAsString(newStudentInsertDTO);

    mockMvc.perform(post("/students")
            .content(jsonBody)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isUnprocessableEntity());
  }

  @Test
  void insertShouldReturnUnprocessableEntityWhenLastNameSizeIsInValid() throws Exception {
    newStudentInsertDTO.setLastName("bra");
    String jsonBody = objectMapper.writeValueAsString(newStudentInsertDTO);

    mockMvc.perform(post("/students")
            .content(jsonBody)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isUnprocessableEntity());
  }

  @Test
  void insertShouldReturnUnprocessableEntityWhenDataIsInvalid() throws Exception {
    newStudentInsertDTO.setEnrollment("");
    newStudentInsertDTO.setName("");
    newStudentInsertDTO.setLastName("");
    String jsonBody = objectMapper.writeValueAsString(newStudentInsertDTO);

    mockMvc.perform(post("/students")
            .content(jsonBody)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isUnprocessableEntity());
  }

  @Test
  void updateShouldReturnStudentDTOWhenEnrollmentExists() throws Exception {
    String expectedName = existingStudent.getName();
    String expectedLastName = existingStudent.getLastName();
    String jsonBody = objectMapper.writeValueAsString(existingStudent);

    mockMvc.perform(put("/students/{enrollment}", existingEnrollment)
            .content(jsonBody)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.name").exists())
            .andExpect(jsonPath("$.name").value(expectedName))
            .andExpect(jsonPath("$.lastName").value(expectedLastName));
  }

  @Test
  void updateShouldReturnNotFoundWhenEnrollmentDoesNotExists() throws Exception {
    String jsonBody = objectMapper.writeValueAsString(existingStudent);

    mockMvc.perform(put("/students/{enrollment}", nonExistingEnrollment)
            .content(jsonBody)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNotFound());
  }

  @Test
  void updateShouldReturnUnprocessableEntityWhenNameIsInvalid() throws Exception {
    existingStudent.setName("");
    String jsonBody = objectMapper.writeValueAsString(existingStudent);

    mockMvc.perform(put("/students/{enrollment}", existingEnrollment)
            .content(jsonBody)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isUnprocessableEntity());
  }

  @Test
  void updateShouldReturnUnprocessableEntityWhenNameSizeIsInvalid() throws Exception {
    existingStudent.setName("gab");
    String jsonBody = objectMapper.writeValueAsString(existingStudent);

    mockMvc.perform(put("/students/{enrollment}", existingEnrollment)
            .content(jsonBody)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isUnprocessableEntity());
  }

  @Test
  void updateShouldReturnUnprocessableEntityWhenLastNameIsInvalid() throws Exception {
    existingStudent.setLastName("");
    String jsonBody = objectMapper.writeValueAsString(existingStudent);

    mockMvc.perform(put("/students/{enrollment}", existingEnrollment)
            .content(jsonBody)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isUnprocessableEntity());
  }

  @Test
  void updateShouldReturnUnprocessableEntityWhenLastNameSizeIsInvalid() throws Exception {
    existingStudent.setLastName("bra");
    String jsonBody = objectMapper.writeValueAsString(existingStudent);

    mockMvc.perform(put("/students/{enrollment}", existingEnrollment)
            .content(jsonBody)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isUnprocessableEntity());
  }

  @Test
  void updateShouldReturnUnprocessableEntityWhenDataIsInvalid() throws Exception {
    existingStudent.setName("");
    existingStudent.setLastName("");
    String jsonBody = objectMapper.writeValueAsString(existingStudent);

    mockMvc.perform(put("/students/{enrollment}", existingEnrollment)
            .content(jsonBody)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isUnprocessableEntity());
  }

  @Test
  void deleteShouldReturnNoContentWhenEnrollmentExists() throws Exception {
    mockMvc.perform(delete("/students/{enrollment}", existingEnrollment)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNoContent());
  }

  @Test
  void deleteShouldReturnNotFoundWhenEnrollmentDoesNotExists() throws Exception {
    mockMvc.perform(delete("/students/{enrollment}", nonExistingEnrollment)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNotFound());
  }
}
