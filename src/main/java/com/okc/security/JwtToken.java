package com.okc.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Object to return as body in JWT Authentication.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtToken {
    private String token;
}
