/**
 * 
 */
package com.bhargav.auth.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bhargav.auth.service.AuthService;

/**
 * @author nsrikantaiah
 */
public class RestAuthenticationFilter implements Filter {

  public static final String AUTHENTICATION_HEADER = "Authorization";

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    if (request instanceof HttpServletRequest) {
      final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
      final String authCredentials = httpServletRequest.getHeader(AUTHENTICATION_HEADER);

      final AuthService authService = new AuthService();
      final boolean authenticationStatus = authService.authenticate(authCredentials);

      if (authenticationStatus) {
        chain.doFilter(request, response);
      } else {
        if (response instanceof HttpServletResponse) {
          HttpServletResponse httpServletResponse = (HttpServletResponse) response;
          httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
      }
    }
  }

  public void init(FilterConfig filterConfig) throws ServletException {
    // No Implementation
  }

  public void destroy() {
    // No Implementation
  }

}
