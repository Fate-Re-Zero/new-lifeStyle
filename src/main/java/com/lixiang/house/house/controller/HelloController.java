package com.lixiang.house.house.controller;

import com.lixiang.house.house.common.model.User;
import com.lixiang.house.house.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author lixiang1234_李祥
 * @site www.lixiang.com
 * @create 2020-03-08 16:12
 */
@Controller
public class HelloController {

    @Autowired
    private UserService userService;

    @RequestMapping("/hello")
    public String getHello(ModelMap modelMap){
        List<User> list = userService.getUsers();
        User one = list.get(0);
        if (one != null){
            throw new IllegalArgumentException();
        }
        modelMap.put("user",one);
        return "hello";
    }

//    @RequestMapping("/index")
//    public String index(){
//        return "homepage/index";
//    }
}
