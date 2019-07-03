package com.okc.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum  UserErrorCode  {

    /**
     *
     */

    USER_NOT_FOUND(404, "用户不存在");

    private Integer code;
    private String msg;
}
