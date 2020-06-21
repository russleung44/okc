package com.okc.controller;

import com.okc.common.vo.Result;
import com.okc.error.NullOrEmptyException;
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

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users")
    @ApiOperation(value = "用户列表")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Result<List<User>> getUsers() throws NullOrEmptyException {

        return new Result<>(userService.getAll());

    }
}
