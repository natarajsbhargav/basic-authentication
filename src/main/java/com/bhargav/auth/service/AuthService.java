/**
 * 
 */
package com.bhargav.auth.service;

import java.io.IOException;
import java.util.Base64;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author nsrikantaiah
 */
public class AuthService {

  private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

  public boolean authenticate(final String authCredentials) {

    if (null == authCredentials) {
      return false;
    }

    // Header value format for Basic authentication will be "Basic encodedstring"
    final String encodedUserAuthCredentials = authCredentials.replaceFirst("Basic ", "");
    String decodedUserAuthCredentials = null;
    try {
      byte[] decodedBytes = Base64.getDecoder().decode(encodedUserAuthCredentials);
      decodedUserAuthCredentials = new String(decodedBytes, "UTF-8");
    } catch (IOException e) {
      LOGGER.error("errorMessage: {}", e.getMessage(), e);
    }

    final StringTokenizer tokenizer = new StringTokenizer(decodedUserAuthCredentials, ":");
    final String userName = tokenizer.nextToken();
    final String userPassword = tokenizer.nextToken();

    return "admin".equals(userName) && "admin".equals(userPassword);
  }

}
