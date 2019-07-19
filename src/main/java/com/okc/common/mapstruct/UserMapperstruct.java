package com.okc.common.mapstruct;

import com.okc.mgb.model.User;
import com.okc.common.vo.UserVO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapperstruct {

    /**
     * User映射UserVO
     * @param users
     * @return
     */
    List<UserVO> toUserVOS(List<User> users);
}
