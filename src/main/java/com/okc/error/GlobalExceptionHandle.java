package com.okc.error;

import com.okc.common.vo.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandle {


    /**
     * 处理业务异常
     * @param e 业务异常
     * @return ErrorInfo
     */
    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public ResultInfo handleBusinessException(BusinessException e) {

        log.error(e.getMessage());

        return new ResultInfo(e.getCode(), e.getMessage());

    }

    /**
     * 处理系统异常
     * @param e 系统异常
     * @return ErrorInfo
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResultInfo handleException(Exception e) {

        log.error(e.getMessage(), e);

        return new ResultInfo(500, e.getMessage());

    }
}
