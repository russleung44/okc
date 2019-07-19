package com.okc.security;

import com.okc.common.constants.AuthoritiesConstants;
import com.okc.mgb.mapper.RoleMapper;
import com.okc.mgb.mapper.RoleNodeMapper;
import com.okc.mgb.mapper.UserMapper;
import com.okc.mgb.model.Role;
import com.okc.mgb.model.User;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * AuthenticationProvider方式验证
 */
@Slf4j
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    private UserMapper userMapper;
    private RoleMapper roleMapper;
    private RoleNodeMapper roleNodeMapper;

    public MyAuthenticationProvider(UserMapper userMapper, RoleMapper roleMapper, RoleNodeMapper roleNodeMapper) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.roleNodeMapper = roleNodeMapper;
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
        User user = Optional.ofNullable(userMapper.selectByUserName(username))
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
        String md5Password = CommonUtil.MD5(RSAUtil.decrypt(password));
        if (!Objects.equals(md5Password, user.getPassword())) {
            throw new UsernameNotFoundException("密码错误");
        }
        Integer roleId = user.getRoleId();
        Role role = roleMapper.selectByPrimaryKey(roleId);
        List<String> auths = roleNodeMapper.selectByRoleId(roleId);
        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        if (auths.isEmpty()) {
            grantedAuths.add(new SimpleGrantedAuthority(AuthoritiesConstants.ANONYMOUS));
        } else {
            auths.forEach(path -> grantedAuths.add(new SimpleGrantedAuthority(path)));
        }
        log.info("后台用户登录: 用户名:{}, 角色:{}", username, role.getName());
        return new UsernamePasswordAuthenticationToken(username, password, grantedAuths);
    }
}
