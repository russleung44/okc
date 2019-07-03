package com.okc.service;

import com.okc.common.vo.UserVO;
import com.okc.error.BusinessException;


public interface UserService {

    /**
     * 测试接口
     * @param userId 用户ID
     * @return
     * @throws BusinessException
     */
    UserVO test(Integer userId) throws BusinessException;
}
