package com.lixiang.house.house.controller;

import com.lixiang.house.house.common.constants.CommonConstants;
import com.lixiang.house.house.common.constants.HouseUserType;
import com.lixiang.house.house.common.model.*;
import com.lixiang.house.house.common.page.PageData;
import com.lixiang.house.house.common.page.PageParams;
import com.lixiang.house.house.common.result.ResultMsg;
import com.lixiang.house.house.interceptor.UserContext;
import com.lixiang.house.house.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author lixiang1234_李祥
 * @site www.lixiang.com
 * @create 2020-03-10 14:23
 */
@Controller
public class HouseController {

    @Autowired
    private HouseService houseService;

    @Autowired
    private CityService cityService;

    @Autowired
    private AgencyService agencyService;

    @Autowired
    private RecommendService recommendService;

    @Autowired
    private CommentService commentService;

    /**
     * 1.实现分页
     * 2.支持小区搜索，类型搜索
     * 3.支持排序
     * 4.支持展示图片，价格，标题，地址等信息
     * @return
     */
    @RequestMapping("/house/list")
    public String houseList(Integer pageSize, Integer pageNum, House query, ModelMap modelMap){
        //这个查询方法不光要返回list，还要返回查询的总数，这样有利于前端分页
        PageData<House> ps = houseService.queryHouse(query, PageParams.build(pageSize, pageNum));
        List<House> hotHouse = recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
        modelMap.put("recomHouses",hotHouse);
        modelMap.put("ps",ps);
        modelMap.put("vo",query);
        return "house/listing";
    }

    /**
     * 添加房产功能
     * @param modelMap
     * @return
     */
    @RequestMapping("house/toAdd")
    public String toAdd(ModelMap modelMap){
        modelMap.put("citys",cityService.getAllCitys());
        modelMap.put("communitys",houseService.getAllCommunitys());
        return "house/add";
    }

    /**
     * 1.获取用户
     * 2.设置房产上限状态
     * 3.添加房产到数据库
     * @param house
     * @return
     */
    @RequestMapping("house/add")
    public String doAdd(House house){
        User user = UserContext.getUser();
        house.setState(CommonConstants.HOUSE_STATE_UP);
        houseService.addHouse(house,user);
        return "redirect:/house/ownlist";
    }

    //个人房产列表
    @RequestMapping("house/ownlist")
    public String ownlist(House house,Integer pageSize,Integer pageNum,ModelMap modelMap){
        User user = UserContext.getUser();
        house.setUserId(user.getId());
        house.setBookmarked(false);
        modelMap.put("ps",houseService.queryHouse(house,PageParams.build(pageSize,pageNum)));
        modelMap.put("pageType","own");
        return "house/ownlist";
    }

    /**
     * 查询房屋详情
     * 查询关联经纪人
     * @param id
     * @return
     */
    @RequestMapping("house/detail")
    public String houseDetail(Long id,ModelMap modelMap) {
//        System.out.println(id);
        House house = houseService.queryOneHouse(id);
        HouseUser houseUser = houseService.getHouseUser(id);
        recommendService.increase(id);
        List<Comment> comments = commentService.getHouseComments(id,8);
        if (houseUser.getUserId() != null && !houseUser.getUserId().equals(0)){
            modelMap.put("agent",agencyService.getAgentDeail(houseUser.getUserId()));
        }
        List<House> rcHouses = recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
        modelMap.put("recomHouses",rcHouses);
        modelMap.put("house",house);
        modelMap.put("commentList", comments);
        return "/house/detail";
    }

    /**
     * 留言功能的实现
     * @param userMsg
     * @return
     */
    @RequestMapping("house/leaveMsg")
    public String houseMsg(UserMsg userMsg){
        houseService.addUserMsg(userMsg);
        return "redirect:/house/detail?id=" + userMsg.getHouseId() + ResultMsg.successMsg("留言成功").asUrlParams();
    }

    //1.评分
    @ResponseBody
    @RequestMapping("house/rating")
    public ResultMsg houseRate(Double rating,Long id){
        houseService.updateRating(id,rating);
        return ResultMsg.successMsg("ok");
    }

    //2.收藏
    @ResponseBody
    @RequestMapping("house/bookmark")
    public ResultMsg bookmark(Long id){
        User user = UserContext.getUser();
        houseService.bindUser2House(id,user.getId(),true);
        return ResultMsg.successMsg("ok");
    }

    //3.删除收藏，其实就是一个解除绑定关系的操作
    @ResponseBody
    @RequestMapping("house/unbookmark")
    public ResultMsg unbookmark(Long id){
        User user = UserContext.getUser();
        houseService.unbindUser2House(id,user.getId(), HouseUserType.BOOKMARK);
        return ResultMsg.successMsg("ok");
    }

    @RequestMapping("house/del")
    public String delsale(Long id,String pageType){
        User user = UserContext.getUser();
        houseService.unbindUser2House(id,user.getId(),pageType.equals("own") ? HouseUserType.SALE : HouseUserType.BOOKMARK);
        return "redirect:/house/ownlist";
    }



    //4.收藏列表
    @RequestMapping("house/bookmarked")
    public String bookmarked(House house,Integer pageSize,Integer pageNum,ModelMap modelMap){
        User user = UserContext.getUser();
        house.setBookmarked(true);
        house.setUserId(user.getId());
        modelMap.put("ps",houseService.queryHouse(house,PageParams.build(pageSize,pageNum)));
        modelMap.put("pageType","book");
        return "house/ownlist";
    }
}