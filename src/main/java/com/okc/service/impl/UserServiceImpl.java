package com.okc.service.impl;

import com.okc.base.BaseMapper;
import com.okc.common.vo.UserVO;
import com.okc.error.NullOrEmptyException;
import com.okc.mgb.mapper.UserMapper;
import com.okc.common.mapstruct.UserMapperstruct;
import com.okc.mgb.model.User;
import com.okc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


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
    public UserMapper getMapper() {
        return userMapper;
    }

    @Override
    public List<UserVO> getUsers() throws NullOrEmptyException {
        List<User> users = getAll();
        return userMapperstruct.toUserVOS(users);
    }
}
