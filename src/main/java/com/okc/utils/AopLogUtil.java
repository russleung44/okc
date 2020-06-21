package com.okc.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Enumeration;


@SuppressWarnings("Duplicates")
@Slf4j
@Component
public class AopLogUtil {


    /**
     * 环绕记录请求
     *
     * @param joinPoint 切点
     * @throws Throwable 异常林北
     */
    public static Object aroundLog(ProceedingJoinPoint joinPoint) throws Throwable {

        log.info("OperationLogAop()");


        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return null;
        }
        HttpServletRequest request = attributes.getRequest();

        // token
        String token = request.getHeader("Authorization");
        // 请求方式
        String method = request.getMethod();
        // URL
        String url = request.getRequestURL().toString();
        // 请求IP
        String ip = CommonUtil.getIp(request);
        // 请求参数
        Object[] args = joinPoint.getArgs();
        StringBuilder params = new StringBuilder();
        for (Object object: args){
            params.append(object).append(" ");
        }
        // 切点签名
        Signature signature = joinPoint.getSignature();
        // 类名
        String className = joinPoint.getTarget().getClass().getName();
        // 方法名
        String methodName = signature.getName();

        log.info("请求地址:{}", url);
        log.info("请求方式:{}", method);
        log.info("请求IP:{}", ip);
        log.info("请求方法:{}", className + "." + methodName);
        log.info("token:{}", token);
        log.info("请求参数:{}", params);

        // 请求开始时间
        LocalDateTime start = LocalDateTime.now();

        // 执行请求
        Object result = joinPoint.proceed();

        // 请求结束时间
        LocalDateTime end = LocalDateTime.now();

        // 请求耗时
        long millis = Duration.between(start, end).toMillis();

        log.info("当前操作耗时:" + millis + "ms");
        log.info("请求结束，返回数据 :" + JSON.toJSONString(result));

        // 返回请求结果(因为Around必须有返回)
        return result;

    }

    /**
     * 异常输出LOG
     *
     * @param joinPoint 切点
     */
    public static void errorLog(JoinPoint joinPoint, Throwable ex) {

        log.error("=========异常输出()=========");

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return;
        }
        HttpServletRequest request = attributes.getRequest();

        // token
        String token = request.getHeader("Authorization");
        // 请求方式
        String method = request.getMethod();
        // URL
        String url = request.getRequestURL().toString();
        // 请求IP
        String ip = CommonUtil.getIp(request);
        // 请求参数
        Object[] args = joinPoint.getArgs();
        StringBuilder params = new StringBuilder();
        for (Object object: args){
            params.append(object).append(" ");
        }
        // 切点签名
        Signature signature = joinPoint.getSignature();
        // 类名
        String className = joinPoint.getTarget().getClass().getName();
        // 方法名
        String methodName = signature.getName();

        log.error("异常请求地址:{}", url);
        log.error("异常请求方式:{}", method);
        log.error("异常请求IP:{}", ip);
        log.error("异常请求方法:{}", className + "." + methodName);
        log.error("异常token:{}", token);
        log.error("异常请求参数:{}", params);
        log.error("异常信息:", ex);

    }

    public static void globalExLog(HttpServletRequest request, Throwable ex) {

        log.error("=========异常输出()=========");

        // token
        String token = request.getHeader("Authorization");
        // 请求方式
        String method = request.getMethod();
        // URL
        String url = request.getRequestURL().toString();
        // 请求IP
        String ip = CommonUtil.getIp(request);
        // 请求参数
        Enumeration<String> parameterNames = request.getParameterNames();
        StringBuilder params = new StringBuilder();
        while(parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            String value = request.getParameter(name);
            params.append(value).append(" ");
        }
        log.error("异常请求地址:{}", url);
        log.error("异常请求方式:{}", method);
        log.error("异常请求IP:{}", ip);
        log.error("异常token:{}", token);
        log.error("异常请求参数:{}", params);
        log.error("异常信息:", ex);

    }
}
