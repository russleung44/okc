package com.okc.security;

import com.okc.mgb.mapper.UserMapper;
import com.okc.mgb.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * AuthenticationProvider方式验证
 */
@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private UserMapper userMapper;

    public CustomAuthenticationProvider(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

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

        if (username == null || password == null) {
            return null;
        }

        User user = Optional.ofNullable(userMapper.loadUserByName(username))
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
        if (!password.equals(user.getPassword())) {
            throw new BadCredentialsException("密码错误");
        }

        // 搜权用户角色
        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new UsernamePasswordAuthenticationToken(username, password, grantedAuths);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
