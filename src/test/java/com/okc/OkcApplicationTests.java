package com.okc;

import com.okc.common.constants.SystemErrorCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OkcApplicationTests {

//    @Autowired
//    UserDao userDao;

    @Test
    public void mapperTest() {

//        List<User> users = userDao.selectAll();
//        System.out.println(users.toString());


    }


    @Test
    public void enumTest() {

        Integer code = SystemErrorCode.NOT_FOUND.getCode();
        String msg = SystemErrorCode.NOT_FOUND.getMsg();


        System.out.println(code + msg);
        System.out.println();
    }
}
