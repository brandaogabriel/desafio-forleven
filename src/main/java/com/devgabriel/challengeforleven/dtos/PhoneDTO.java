package com.devgabriel.challengeforleven.dtos;

import com.devgabriel.challengeforleven.entities.Phone;

import java.io.Serializable;

public class PhoneDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  private Long id;
  private String phoneNumber;

  public PhoneDTO() {
  }

  public PhoneDTO(Long id, String phoneNumber) {
    this.id = id;
    this.phoneNumber = phoneNumber;
  }

  public PhoneDTO(Phone entity) {
    id = entity.getId();
    phoneNumber = entity.getPhoneNumber();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
}
