package com.okc.error;


import lombok.Getter;

/**
 * 自定义空异常
 */
@Getter
public class NullOrEmptyException extends Exception {

    private Integer code;
    private String message;

    /**
     * 默认返回
     */
    public NullOrEmptyException() {
        this.code = 904;
        this.message = "暂无数据";
    }

    /**
     * 自定义带信息返回
     * @param message
     */
    public NullOrEmptyException(String message) {
        this.code = 904;
        this.message = message;
    }
}