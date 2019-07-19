package com.okc.base;


import com.okc.error.NullOrEmptyException;

import java.util.List;
import java.util.Optional;


/**
 * JDK8函数式接口注解 仅能包含一个抽象方法
 * @param <E>
 */
@FunctionalInterface
public interface BaseService<E> {

    /**
     * 获取Mapper
     * @return
     */
    public BaseMapper<E> getMapper();

    /**
     * 主键查询
     * @param id
     * @return
     * @throws NullOrEmptyException
     */
    public default E get(Object id) throws NullOrEmptyException {
        return Optional.ofNullable(getMapper().selectByPrimaryKey(id)).orElseThrow(NullOrEmptyException::new);
    }

    /**
     * 获取所有列表
     * @return
     * @throws NullOrEmptyException
     */
    public default List<E> getAll() throws NullOrEmptyException {
        return Optional.ofNullable(getMapper().selectAll()).orElseThrow(NullOrEmptyException::new);
    }


}
