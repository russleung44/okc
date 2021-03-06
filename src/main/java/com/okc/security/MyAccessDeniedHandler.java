package com.okc.security;

import cn.hutool.json.JSONUtil;
import com.okc.common.constants.SystemErrorCode;
import com.okc.common.vo.Result;
import com.okc.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author TonyLeung
 */
@Slf4j
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
        log.error(
                "请求的url: {},请求的header头: {},请求的IP地址: {}",
                request.getRequestURL().toString(),
                request.getHeader("Authorization"),
                CommonUtil.getIp(request));

        response.setCharacterEncoding("UTF-8");
        response.setStatus(403);
        PrintWriter printWriter = response.getWriter();
        printWriter.write(JSONUtil.toJsonPrettyStr(new Result<>(SystemErrorCode.FORBIDDEN)));
        printWriter.flush();
    }
}
