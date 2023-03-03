package com.openbootcamp.jwt.course_jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class NewUser {

  private String username;
  private String password;

  private String email;

  private String firstName;
  private String lastName;
  private Integer age;

  private String rol;
}
