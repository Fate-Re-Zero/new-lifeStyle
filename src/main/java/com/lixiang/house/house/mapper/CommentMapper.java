package com.lixiang.house.house.mapper;

import com.lixiang.house.house.common.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lixiang1234_李祥
 * @site www.lixiang.com
 * @create 2020-03-12 17:10
 */
@Mapper
@Repository
public interface CommentMapper {

    int insert(Comment comment);

    List<Comment> selectComments(@Param("houseId")long houseId, @Param("size")int size);

    List<Comment> selectBlogComments(@Param("blogId")long blogId, @Param("size")int size);
}
