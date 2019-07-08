package com.okc.service;

import com.okc.error.BusinessException;
import com.okc.error.CustomNullPointerException;
import com.okc.mgb.model.User;

import java.util.List;


public interface UserService {

    /**
     * 用户列表
     * @return
     * @throws CustomNullPointerException
     */
    List<User> getUsers() throws CustomNullPointerException;
}
