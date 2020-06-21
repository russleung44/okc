package com.okc.security;

import com.okc.common.constants.SystemErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class MyAccessDecisionManager implements AccessDecisionManager {

    /**
     * 检查用户是否够权限访问资源
     *
     * @param authentication   从spring的全局缓存SecurityContextHolder中拿到的，里面是用户的权限信息
     * @param object           url
     * @param configAttributes 所需的权限
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        if (configAttributes == null) {
            return;
        }
        if (authentication == null) {
            throw new AccessDeniedException(SystemErrorCode.FORBIDDEN.getMsg());
        }

        if ("tony".equals(authentication.getPrincipal())) {
            return;
        }

        for (ConfigAttribute attribute : configAttributes) {
            String needPermission = attribute.getAttribute();
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (StringUtils.equals(authority.getAuthority(), needPermission)) {
                    return;
                }
            }
        }
        throw new AccessDeniedException(SystemErrorCode.FORBIDDEN.getMsg());
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {return true; }

}
