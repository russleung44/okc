package com.okc.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SystemErrorCode {

    /**
     * 异常码
     */

    OK(200, "请求成功"),
    NOT_FOUND(404, "不存在"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "无权限"),
    SYSTEM_ERROR(500, "系统异常"),
    BAD_GATEWAY(502, "网关错误"),
    SERVICE_UNAVAILABLE(503, "服务不可用"),
    GATEWAY_TIMEOUT(504, "网关超时"),
    VERIFY_FAILED(901, "验证失败");


    private Integer code;
    private String msg;


}
