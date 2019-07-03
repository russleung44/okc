package com.okc.common.mapper;

import com.okc.common.model.User;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface UserMapper extends Mapper<User>, MySqlMapper<User> {

    /**
     * 测试接口
     * @return
     */
    List<User> test();
}

