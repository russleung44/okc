package com.okc.common.vo;

import com.okc.common.constants.SystemErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    private Integer code;
    private String message;
    private T data;

    public Result(T data) {
        this.code = 200;
        this.message = "请求成功";
        this.data = data;
    }

    public Result(String message) {
        this.code = 500;
        this.message = message;
        this.data = null;
    }

    public Result(T data, String message) {
        this.code = 500;
        this.message = message;
        this.data = data;
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }

    public Result(SystemErrorCode systemErrorCode) {
        this.code = systemErrorCode.getCode();
        this.message = systemErrorCode.getMsg();
        this.data = null;
    }
}
