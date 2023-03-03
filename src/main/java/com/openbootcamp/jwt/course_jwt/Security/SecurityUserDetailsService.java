package com.openbootcamp.jwt.course_jwt.Security;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.openbootcamp.jwt.course_jwt.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SecurityUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var userOp = userRepository.findOneByEmail(username)
      .orElseThrow(() -> new UsernameNotFoundException("El usuario ingresado no existe"));
    return new UserDetailsImp(userOp);
  }
  
}
