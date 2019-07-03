package com.okc.config;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

import java.sql.Types;


public class CustomJavaTypeResolverDefaultImpl extends JavaTypeResolverDefaultImpl {

    public CustomJavaTypeResolverDefaultImpl() {
        super();
        //把数据库的 TINYINT 映射成 Integer
        super.typeMap.put(Types.TINYINT,
                new JdbcTypeInformation("TINYINT",
                        new FullyQualifiedJavaType(Integer.class.getName())));

    }
}

