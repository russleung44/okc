package com.okc.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";

    private static final String SECRET_KEY = "okc";

    /**
     * 根据权限创建Token
     *
     * @param authentication
     * @return
     */
    public static String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        long tokenValidityTime = 1000 * 60 * 60 * 24 * 7L;
        Date validity = new Date(now + tokenValidityTime);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY.getBytes())
                .setExpiration(validity)
                .compact();
    }

    /**
     * token验证
     *
     * @param authToken
     * @return
     */
    public static boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(authToken);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("token已过期:{}", authToken);
        } catch (UnsupportedJwtException e) {
            log.error("token格式错误:{}", authToken);
        } catch (SignatureException e) {
            log.error("签名验证失败:{}", authToken);
        }
        return false;
    }

    /**
     * 解析token获取权限
     *
     * @param token
     * @return
     */
    public static Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes())
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(claims.getSubject(), "", authorities);
    }

}
