package com.openbootcamp.jwt.course_jwt.Services.Imp;

import java.util.HashMap;
import java.util.Map;

import javax.management.relation.RoleNotFoundException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.openbootcamp.jwt.course_jwt.Model.User;
import com.openbootcamp.jwt.course_jwt.Repository.RoleRepository;
import com.openbootcamp.jwt.course_jwt.Repository.UserRepository;
import com.openbootcamp.jwt.course_jwt.Security.JwtUtils;
import com.openbootcamp.jwt.course_jwt.Security.SecurityUserDetailsService;
import com.openbootcamp.jwt.course_jwt.Services.IUserService;
import com.openbootcamp.jwt.course_jwt.dto.JwtDto;
import com.openbootcamp.jwt.course_jwt.dto.LoginUser;
import com.openbootcamp.jwt.course_jwt.dto.NewUser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements IUserService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final JwtUtils jwtUtils;

  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final SecurityUserDetailsService userDetailsService;

  @Override
  public Boolean register(NewUser newUser) throws RoleNotFoundException {

    /* verifica en la base de datos los roles */
    var role = roleRepository.findOneByRole(newUser.getRol())
        .orElseThrow(() -> new RoleNotFoundException("Role not exist"));

    log.info(role.toString());

    /* Obtiene el dto y lo transforma en una entidad para ser almacenado */
    var user = User.builder()
        .username(newUser.getUsername())
        .password(passwordEncoder.encode(newUser.getPassword()))
        .email(newUser.getEmail())
        .firstName(newUser.getFirstName())
        .lastName(newUser.getLastName())
        .age(newUser.getAge())
        .role(role)
        .build();

    /* Guardando en BD */
    userRepository.save(user);
    return true;
  }

  @Override
  public JwtDto login(LoginUser loginUser) throws AuthenticationException {
    log.info(loginUser.getUserName());

    /* autenticamos el usuario */
    UserDetails userDetails = userDetailsService.loadUserByUsername(loginUser.getUserName());

    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginUser.getUserName(), loginUser.getPassword()));

    /* generamos el token */
    Map<String, Object> claims = new HashMap<>();
    claims.put("role", userDetails.getAuthorities());

    return new JwtDto(jwtUtils.generateToken(userDetails, claims));
  }

}
