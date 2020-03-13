package com.lixiang.house.house.controller;

import com.lixiang.house.house.common.model.House;
import com.lixiang.house.house.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author lixiang1234_李祥
 * @site www.lixiang.com
 * @create 2020-03-11 19:22
 */
@Controller
public class HomePageController {

    @Autowired
    private RecommendService recommendService;

    @RequestMapping("index")
    public String index(ModelMap modelMap){
        List<House> houses = recommendService.getLastest();
        modelMap.put("recomHouses",houses);
        return "homepage/index";
    }

    @RequestMapping("")
    public String home(ModelMap modelMap){
        return "redirect:/index";
    }
}
