package com.okc.service;

import com.okc.base.BaseService;
import com.okc.error.NullOrEmptyException;
import com.okc.mgb.model.User;

import java.util.List;


public interface UserService extends BaseService<User> {


    List getUsers() throws NullOrEmptyException;
}
