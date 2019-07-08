package com.okc.controller;

import com.okc.common.constants.SystemErrorCode;
import com.okc.common.vo.ResultInfo;
import com.okc.error.BusinessException;
import com.okc.security.JwtToken;
import com.okc.security.TokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "login")
@Slf4j
@RestController
public class LoginController {

    private AuthenticationManager authenticationManager;
    private TokenProvider tokenProvider;

    public LoginController(AuthenticationManager authenticationManager, TokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @ApiOperation(value = "登陆")
    @PostMapping(value = "/login")
    public ResultInfo login(@RequestParam String username, @RequestParam String password) throws BusinessException {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        Authentication authentication;
        try {
            authentication = this.authenticationManager.authenticate(authenticationToken);
        }catch (AuthenticationException e){
            log.error("[error]",e);
            throw new BusinessException(SystemErrorCode.VERIFY_FAILED);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = "Bearer " + tokenProvider.createToken(authentication);
        return new ResultInfo<>(new JwtToken(jwt));
    }
}
