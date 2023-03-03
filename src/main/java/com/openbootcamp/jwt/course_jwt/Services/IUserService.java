package com.openbootcamp.jwt.course_jwt.Services;

import javax.management.relation.RoleNotFoundException;

import com.openbootcamp.jwt.course_jwt.dto.JwtDto;
import com.openbootcamp.jwt.course_jwt.dto.LoginUser;
import com.openbootcamp.jwt.course_jwt.dto.NewUser;

public interface IUserService {

  Boolean register(NewUser newUser) throws RoleNotFoundException;
  JwtDto login(LoginUser loginUser);

}
