package com.lixiang.house.house.mapper;

import com.lixiang.house.house.common.model.Blog;
import com.lixiang.house.house.common.page.PageParams;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lixiang1234_李祥
 * @site www.lixiang.com
 * @create 2020-03-12 18:54
 */

@Mapper
@Repository
public interface BlogMapper {

    List<Blog> selectBlog(@Param("blog")Blog query, @Param("pageParams") PageParams params);

    Long selectBlogCount(Blog query);
}
