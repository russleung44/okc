package com.okc.error;



import com.okc.common.constants.SystemErrorCode;
import com.okc.common.constants.UserErrorCode;
import lombok.Getter;

/**
 * 业务异常
 */
@Getter
public class BusinessException extends BaseException {

    private Integer code;
    private String message;


    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BusinessException(String message) {
        super(message);
        this.message = message;
    }

    public BusinessException(SystemErrorCode systemErrorCode) {
        this(systemErrorCode.getMsg());
        this.code = systemErrorCode.getCode();
        this.message = systemErrorCode.getMsg();
    }

    public BusinessException(UserErrorCode userErrorCode) {
        this(userErrorCode.getMsg());
        this.code = userErrorCode.getCode();
        this.message = userErrorCode.getMsg();
    }


}