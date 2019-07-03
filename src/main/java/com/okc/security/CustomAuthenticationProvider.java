package com.okc.security;

import com.okc.common.constants.CommonConstant;
import com.okc.error.BusinessException;
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
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

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
        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        try {
            if (validateUser(username, password, grantedAuths)) {
                return new UsernamePasswordAuthenticationToken(username, password, grantedAuths);
            } else {
                return null;
            }
        } catch (BusinessException e) {
            e.printStackTrace();
        }
        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private boolean validateUser(String loginName, String password, List<GrantedAuthority> grantedAuths) throws BadCredentialsException, BusinessException {

        if (loginName == null || password == null) {
            return false;
        }

        // TODO 验证用户

        // 初始化权限
        grantedAuths.add(new SimpleGrantedAuthority(CommonConstant.INITIALIZATION_ROLE_NAME));

        return true;

    }
}
