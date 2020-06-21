package com.okc.mgb.mapper;

import com.okc.base.BaseMapper;
import com.okc.mgb.model.RoleNode;

import java.util.List;

public interface RoleNodeMapper extends BaseMapper<RoleNode> {

    List<String> selectByRoleId(Integer roleId);
}