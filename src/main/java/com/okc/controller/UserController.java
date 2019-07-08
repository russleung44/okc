package com.okc.controller;

import com.okc.common.vo.ResultInfo;
import com.okc.error.CustomNullPointerException;
import com.okc.mgb.model.User;
import com.okc.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "用户接口")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users")
    @ApiOperation(value = "用户列表")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResultInfo<List<User>> getUsers() throws CustomNullPointerException {

        return new ResultInfo<>(userService.getUsers());

    }
}
