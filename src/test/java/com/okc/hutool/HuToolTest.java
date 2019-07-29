package com.okc.hutool;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
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

        JSONObject obj = JSONUtil.createObj();
        obj.put("nothing", "yes");
        obj.put("okc", "westbrook");

        String jsonStr = JSONUtil.toJsonStr(obj);
        System.out.println(jsonStr);

        boolean isJson = JSONUtil.isJson(jsonStr);
        System.out.println(isJson);

        String formatJsonStr = JSONUtil.formatJsonStr(jsonStr);
        System.out.println(formatJsonStr);

        JSONObject jsonObject = JSONUtil.parseObj(jsonStr);
        System.out.println(jsonObject);

    }
}
