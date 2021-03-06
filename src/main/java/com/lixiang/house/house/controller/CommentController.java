package com.lixiang.house.house.controller;

import com.lixiang.house.house.common.model.User;
import com.lixiang.house.house.interceptor.UserContext;
import com.lixiang.house.house.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author lixiang1234_李祥
 * @site www.lixiang.com
 * @create 2020-03-12 18:50
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;


    @RequestMapping(value="comment/leaveComment",method={RequestMethod.POST,RequestMethod.GET})
    public String leaveComment(String content,Long houseId,ModelMap modelMap){
        User user = UserContext.getUser();
        Long userId =  user.getId();
        commentService.addHouseComment(houseId,content,userId);
        return "redirect:/house/detail?id=" + houseId;
    }

    @RequestMapping(value="comment/leaveBlogComment",method={RequestMethod.POST,RequestMethod.GET})
    public String leaveBlogComment(String content, Integer blogId, ModelMap modelMap, RedirectAttributes redirectAttributes){
        User user = UserContext.getUser();
        Long userId =  user.getId();
        commentService.addBlogComment(blogId,content,userId);
        return "redirect:/blog/detail?id=" + blogId;
    }

}
