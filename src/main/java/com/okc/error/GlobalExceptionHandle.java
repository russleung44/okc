package com.okc.error;

import com.okc.common.constants.SystemErrorCode;
import com.okc.common.vo.Result;
import com.okc.utils.AopLogUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandle {


    /**
     * 处理业务异常
     *
     * @param e 业务异常
     * @return ErrorInfo
     */
    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public Result handleBusinessException(BusinessException e) {
        log.error(e.getMessage());
        return new Result(e.getCode(), e.getMessage());
    }

    /**
     * 空处理
     *
     * @return ResultInfo
     */
    @ResponseBody
    @ExceptionHandler(NullOrEmptyException.class)
    public Result handleCustomNullPointerException(NullOrEmptyException ex) {
        log.error("异常码: " + ex.getCode() + ", 异常信息: " + ex.getMessage());
        return new Result(ex.getCode(), ex.getMessage());

    }


    /**
     * 参数请求格式错误
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {

        log.error(ex.getMessage());

        return new Result(SystemErrorCode.PARAM_ERROR);

    }

    /**
     * 处理参数校验异常
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleHttpMessageNotReadableException(MethodArgumentNotValidException ex) {

        log.error(ex.getParameter().toString());
        log.error(ex.getMessage());

        StringBuilder errorMsg = new StringBuilder();
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        for (ObjectError error : errors) {
            errorMsg.append(error.getDefaultMessage()).append(";");
        }

        return new Result(901, errorMsg.toString());

    }


    /**
     * 处理文件异常
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(MultipartException.class)
    public Result handleHttpMessageNotReadableException(MultipartException ex) {

        log.error(ex.getMessage());
        return new Result(902, "文件过大, 不能超过~M");

    }


    @ResponseBody
    @ExceptionHandler(MissingServletRequestPartException.class)
    public Result handleMissingServletRequestPartException(MissingServletRequestPartException ex) {

        log.error(ex.getMessage());
        return new Result(903, "缺少请求参数");

    }

    /**
     * 处理系统异常
     *
     * @param ex 系统异常
     * @return ErrorInfo
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    @AfterThrowing(throwing = "ex", pointcut = "operationLog()")
    public Result handleException(Exception ex, HttpServletRequest request) {
        AopLogUtil.globalExLog(request, ex);
        return new Result(ex.getMessage());
    }
}
