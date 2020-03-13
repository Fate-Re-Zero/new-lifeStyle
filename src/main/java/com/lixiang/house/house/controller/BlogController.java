package com.lixiang.house.house.controller;

import com.lixiang.house.house.common.constants.CommonConstants;
import com.lixiang.house.house.common.model.Blog;
import com.lixiang.house.house.common.model.Comment;
import com.lixiang.house.house.common.model.House;
import com.lixiang.house.house.common.page.PageData;
import com.lixiang.house.house.common.page.PageParams;
import com.lixiang.house.house.service.BlogService;
import com.lixiang.house.house.service.CommentService;
import com.lixiang.house.house.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author lixiang1234_李祥
 * @site www.lixiang.com
 * @create 2020-03-12 18:52
 */
@Controller
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private RecommendService recommendService;

    @RequestMapping(value="blog/list",method={RequestMethod.POST,RequestMethod.GET})
    public String list(Integer pageSize, Integer pageNum, Blog query, ModelMap modelMap){
        PageData<Blog> ps = blogService.queryBlog(query, PageParams.build(pageSize, pageNum));
        List<House> houses =  recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
        modelMap.put("recomHouses", houses);
        modelMap.put("ps", ps);
        return "/blog/listing";
    }

    @RequestMapping(value="blog/detail",method={RequestMethod.POST,RequestMethod.GET})
    public String blogDetail(int id,ModelMap modelMap){
        Blog blog = blogService.queryOneBlog(id);
        List<Comment> comments = commentService.getBlogComments(id,8);
        List<House> houses =  recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
        modelMap.put("recomHouses", houses);
        modelMap.put("blog", blog);
        modelMap.put("commentList", comments);
        return "/blog/detail";
    }

}
