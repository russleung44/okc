package com.okc.security;

import com.alibaba.fastjson.JSON;
import com.okc.common.constants.SystemErrorCode;
import com.okc.common.vo.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException {
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setStatus(401);
        PrintWriter printWriter = httpServletResponse.getWriter();
        ResultInfo apiResultVO = new ResultInfo(SystemErrorCode.UNAUTHORIZED);
        log.error(SystemErrorCode.UNAUTHORIZED.getMsg());
        printWriter.write(JSON.toJSONString(apiResultVO));
        printWriter.flush();
    }
}
