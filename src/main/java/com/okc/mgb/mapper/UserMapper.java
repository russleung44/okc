package com.okc.mgb.mapper;

import com.okc.mgb.model.User;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface UserMapper extends Mapper<User>, MySqlMapper<User> {

    /**
     * 查询用户
     * @param username
     * @return
     */
    User loadUserByName(String username);
}

