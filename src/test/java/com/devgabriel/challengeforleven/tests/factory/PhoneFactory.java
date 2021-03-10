package com.devgabriel.challengeforleven.tests.factory;

import com.devgabriel.challengeforleven.entities.Phone;

public class PhoneFactory {

  public static Phone createPhone() {
    return new Phone(1L, "99988-7744");
  }

}
