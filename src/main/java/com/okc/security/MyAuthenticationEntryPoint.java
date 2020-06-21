package com.okc.security;

import cn.hutool.json.JSONUtil;
import com.okc.common.constants.SystemErrorCode;
import com.okc.common.vo.Result;
import com.okc.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author TonyLeung
 * @date 2020/6/21
 */
@Slf4j
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        log.error(
                "请求的url: {},请求的header头: {},请求的IP地址: {}",
                request.getRequestURL().toString(),
                request.getHeader("Authorization"),
                CommonUtil.getIp(request));

        response.setCharacterEncoding("UTF-8");
        response.setStatus(401);
        PrintWriter printWriter = response.getWriter();
        printWriter.write(JSONUtil.toJsonPrettyStr(new Result<>(SystemErrorCode.UNAUTHORIZED)));
        printWriter.flush();
    }
}
