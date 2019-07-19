package com.okc.mgb.mapper;

import com.okc.base.BaseMapper;
import com.okc.mgb.model.User;

public interface UserMapper extends BaseMapper<User> {
    User selectByUserName(String username);
}