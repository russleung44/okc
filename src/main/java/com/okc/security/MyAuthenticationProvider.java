package com.okc.security;

import com.okc.common.constants.AuthoritiesConstants;
import com.okc.utils.CommonUtil;
import com.okc.utils.RSAUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * AuthenticationProvider方式验证
 */
@Slf4j
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {


    /**
     * 自定义验证方式
     *
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        return validateUser(username, password);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private UsernamePasswordAuthenticationToken validateUser(String username, String password) throws BadCredentialsException {
        if (username == null || password == null) {
            return null;
        }

        // TODO 用户名密码校验

        // TODO 获取角色权限
        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        grantedAuths.add(new SimpleGrantedAuthority(AuthoritiesConstants.ANONYMOUS));

        return new UsernamePasswordAuthenticationToken(username, password, grantedAuths);
    }
}
