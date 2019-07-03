package com.okc.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserVO {
    @ApiModelProperty(value = "用户昵称")
    private String nickName;
    private String password;
}
