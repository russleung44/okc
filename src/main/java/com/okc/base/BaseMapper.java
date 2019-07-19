package com.okc.base;


import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;


public interface BaseMapper<E> extends Mapper<E>, MySqlMapper<E> {}
