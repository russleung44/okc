package com.okc.hutool;

import cn.hutool.core.collection.CollUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HuToolTest {


    @Test
    public void collTest() {

        ArrayList<Object> objects = new ArrayList<>();

        boolean isEmpty = CollUtil.isNotEmpty(objects);

        System.out.println(isEmpty);

    }


    @Test
    public void jsonTest() {



    }
}
