package com.okc.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.okc.common.constants.SystemErrorCode;
import com.okc.common.vo.Result;
import com.okc.utils.AopLogUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setStatus(403);
        PrintWriter printWriter = response.getWriter();
        AopLogUtil.globalExLog(request, e);
        printWriter.write(JSON.toJSONString( new Result<>(SystemErrorCode.FORBIDDEN), SerializerFeature.WriteMapNullValue));
        printWriter.flush();
    }
}
