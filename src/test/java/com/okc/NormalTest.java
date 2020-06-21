package com.okc;

import cn.hutool.core.util.NumberUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@WebAppConfiguration
public class NormalTest {

    @Test
    public void test() {

        int i = new BigDecimal("200.07").setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).intValue();

        System.out.println(i);
    }
}
