package com.okc.service.impl;

import com.okc.error.CustomNullPointerException;
import com.okc.mgb.mapper.UserMapper;
import com.okc.common.mapstruct.UserMapperstruct;
import com.okc.mgb.model.User;
import com.okc.error.BusinessException;
import com.okc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;
    private UserMapperstruct userMapperstruct;

    public UserServiceImpl(UserMapper userMapper, UserMapperstruct userMapperstruct) {
        this.userMapper = userMapper;
        this.userMapperstruct = userMapperstruct;
    }



    @Override
    public List<User> getUsers() throws CustomNullPointerException {
        return Optional.ofNullable(userMapper.selectAll()).orElseThrow(CustomNullPointerException::new);
    }
}
