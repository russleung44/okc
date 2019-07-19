package com.okc.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider {


    private static final String AUTHORITIES_KEY = "auth";

    private String secretKey;

    private long tokenValidityInMillisecondsForRememberMe;

    @PostConstruct
    public void init() {
        this.secretKey = "b49a7bdab7a3bd751d263f7cfd92879e";

        int millisecondIn1day = 1000 * 60 * 60 * 24;
        this.tokenValidityInMillisecondsForRememberMe = millisecondIn1day * 7L;
    }

    /**
     * 根据权限创建Token
     *
     * @param authentication
     * @return
     */
    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .setExpiration(validity)
                .compact();
    }

    /**
     * token验证
     *
     * @param authToken
     * @return
     */
    public boolean validateToken(String authToken) {
        Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
        return true;
    }

    /**
     * 解析token获取权限
     *
     * @param token
     * @return
     */
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(claims.getSubject(), "", authorities);
    }

}
