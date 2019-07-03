package com.okc.controller;



import com.okc.common.vo.ResultInfo;
import com.okc.common.vo.UserVO;
import com.okc.error.BusinessException;
import com.okc.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "test")
@RequestMapping("/test")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/user")
    @ApiOperation(value = "测试接口")
    public ResultInfo<UserVO> helloWorld(@ApiParam(value = "用户ID") @RequestParam(value = "userId") Integer userId) throws BusinessException {

        return new ResultInfo<>(userService.test(userId));

    }
}
