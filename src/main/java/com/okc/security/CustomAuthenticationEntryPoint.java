package com.okc.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.okc.common.vo.ResultInfo;
import com.okc.common.constants.SystemErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setStatus(401);
        PrintWriter printWriter = httpServletResponse.getWriter();
        log.error("[error]",e);
        printWriter.write(JSON.toJSONString( new ResultInfo<>(SystemErrorCode.UNAUTHORIZED), SerializerFeature.WriteMapNullValue));
        printWriter.flush();
    }
}
