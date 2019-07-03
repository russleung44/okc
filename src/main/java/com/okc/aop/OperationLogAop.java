package com.okc.aop;

import com.okc.utils.AopLogUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class OperationLogAop {

    /**
     * 切点 : controller下的所有
     */
    @Pointcut(value = "within(com.okc.controller..*)")
    public void operationLog() {}


    /**
     * 环绕记录日志
     * @param joinPoint 切点
     * @return 环绕必须返回, 然而不知道有什么用
     * @throws Throwable 异常林北
     */
    @Around("operationLog()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        return AopLogUtil.aroundLog(joinPoint);
    }

    @AfterThrowing(throwing="ex", pointcut = "operationLog()")
    public void afterThrowing(JoinPoint joinPoint, Throwable ex)  {
        AopLogUtil.errorLog(joinPoint, ex);
    }
}