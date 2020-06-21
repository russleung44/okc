package com.okc.controller;

import com.okc.common.constants.SystemErrorCode;
import com.okc.common.vo.Result;
import com.okc.error.BusinessException;
import com.okc.security.JwtToken;
import com.okc.security.MyAuthenticationProvider;
import com.okc.security.TokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author TonyLeung
 */
@Api(tags = "login")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginController {

    private final TokenProvider tokenProvider;
    private final MyAuthenticationProvider authenticationProvider;

    @ApiOperation(value = "登陆")
    @PostMapping(value = "/login")
    public Result login(@RequestParam String username, @RequestParam String password) throws BusinessException {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        Authentication authentication;
        try {
            authentication = this.authenticationProvider.authenticate(authenticationToken);
        }catch (AuthenticationException e){
            log.error("[error]",e);
            throw new BusinessException(SystemErrorCode.VERIFY_FAILED);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = "Bearer " + tokenProvider.createToken(authentication);
        System.out.println("jwt = " + jwt);
        return new Result<>(new JwtToken(jwt));
    }
}
