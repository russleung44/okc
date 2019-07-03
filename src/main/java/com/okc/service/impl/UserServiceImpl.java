package com.okc.service.impl;

import com.okc.common.mapper.UserMapper;
import com.okc.common.mapstruct.UserMapperstruct;
import com.okc.common.model.User;
import com.okc.common.vo.UserVO;
import com.okc.common.constants.UserErrorCode;
import com.okc.error.BusinessException;
import com.okc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


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
    public UserVO test(Integer userId) throws BusinessException {

        User user = userMapper.selectByPrimaryKey(userId);
        if (user != null) {
            return userMapperstruct.toUserVO(user);
        }

        throw new BusinessException(UserErrorCode.USER_NOT_FOUND);
    }
}
