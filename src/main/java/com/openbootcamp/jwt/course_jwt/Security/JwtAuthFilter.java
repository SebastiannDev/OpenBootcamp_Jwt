package com.openbootcamp.jwt.course_jwt.Security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Clase encargada de filtrar las request, verifica que el jwt sea valido
 * y autentitica en la aplicación.
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

  private final UserDetailsService userDetailsService;
  private final JwtUtils jwtUtils;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    final String jwtToken = getToken(request);

    if (jwtToken != null) {
      final String userEmail = jwtUtils.extractUsername(jwtToken);

      /*
       * Verifica si existe una sessión en la aplicación o no contiene un usuario el
       * token.
       */
      if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
        Boolean tokenValidated = jwtUtils.isTokenValid(jwtToken);
        log.info("" + tokenValidated);

        if (tokenValidated) {
          UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null,
              userDetails.getAuthorities());

          authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authToken);
          log.info("Filter passed");

        }
      }
    }

    filterChain.doFilter(request, response);
  }

  /**
   * @param request {@code HttpServletRequest}
   * @return {@code String} si contiene un Header con un token o {@code Null}
   */
  private String getToken(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");

    if (authHeader != null && authHeader.startsWith("Bearer")) {
      return authHeader.substring(7);
    }
    return null;
  }

}
