package com.lixiang.house.house;

import com.lixiang.house.house.common.model.User;
import com.lixiang.house.house.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author lixiang1234_李祥
 * @site www.lixiang.com
 * @create 2020-03-10 13:56
 */
@RunWith(SpringRunner.class)//是一个junit注解，SpringRunner指的需要通过spring测试框架的支持
//如果想要启动端口，就加上下面括号的启动环境
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)//表示测试的时候可以用springboot的一些特性
public class AuthTest {

    @Autowired
    private UserService userService;

    @Test
    public void authTest(){
        User user = userService.auth("fate_rezero@163.com", "li19971008");
        assert user != null;
        System.out.println(user.getAboutme());
    }


}
