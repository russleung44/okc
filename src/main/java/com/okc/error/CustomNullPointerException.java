package com.okc.error;


import lombok.Getter;

/**
 * 自定义空异常
 */
@Getter
public class CustomNullPointerException extends Exception {

    private Integer code;
    private String message;

    /**
     * 默认返回
     */
    public CustomNullPointerException() {
        this.code = 904;
        this.message = "暂无数据";
    }

    /**
     * 自定义带信息返回
     * @param message
     */
    public CustomNullPointerException(String message) {
        this.code = 904;
        this.message = message;
    }
}