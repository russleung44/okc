package com.okc.common.mapstruct;

import com.okc.mgb.model.User;
import com.okc.common.vo.UserVO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapperstruct {

    /**
     * User映射UserVO
     * @param user
     * @return
     */
    @Mapping(source = "username", target = "nickName")
    UserVO toUserVO(User user);
}
