package com.okc.security;

import cn.hutool.core.util.StrUtil;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.security.Permission;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * @author tony
 * @date 2019/7/10 18:34
 */
@Component
public class MySecurityMetadataSource implements
        FilterInvocationSecurityMetadataSource {

    private HashMap<String, Collection<ConfigAttribute>> map =null;


    /**
     * 加载权限表中所有操作请求权限
     */
    private void loadResourceDefine() {
        map = new HashMap<>(50);
        Collection<ConfigAttribute> configAttributes ;
        ConfigAttribute cfg;
        List<Permission> permissions = null;
        for(Permission permission : permissions) {
            configAttributes  = new ArrayList<>();
            cfg = new SecurityConfig(permission.getName());
            // 此处添加的信息将会作为MyAccessDecisionManager类的decide的第三个参数。
            configAttributes .add(cfg);
            // 用权限的getUrl() 作为map的key，用ConfigAttribute的集合作为 value，
            map.put(permission.getActions(), configAttributes);
        }
    }

    /**
     * 判定用户请求的url是否在权限表中
     * 如果在权限表中，则返回给decide方法，用来判定用户是否有此权限
     * 如果不在权限表中则放行
     * @param o
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {

        if(map == null){
            loadResourceDefine();
        }
        //Object中包含用户请求request
        String url = ((FilterInvocation) o).getRequestUrl();
        PathMatcher pathMatcher = new AntPathMatcher();
        for (String resURL : map.keySet()) {
            if (StrUtil.isNotBlank(resURL) && pathMatcher.match(resURL, url)) {
                return map.get(resURL);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
