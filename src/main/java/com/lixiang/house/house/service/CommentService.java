package com.lixiang.house.house.service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.lixiang.house.house.common.constants.CommonConstants;
import com.lixiang.house.house.common.model.Comment;
import com.lixiang.house.house.common.model.User;
import com.lixiang.house.house.common.utils.BeanHelper;
import com.lixiang.house.house.mapper.CommentMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lixiang1234_李祥
 * @site www.lixiang.com
 * @create 2020-03-12 16:53
 */
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserService userService;

    public void addHouseComment(Long houseId, String content, Long userId) {
        addComment(houseId,null, content, userId,1);
    }

    @Transactional(rollbackFor=Exception.class)
    public void addComment(Long houseId,Integer blogId, String content, Long userId,int type) {
        Comment comment = new Comment();
        if (type == 1) {
            comment.setHouseId(houseId);
        }else {
            comment.setBlogId(blogId);
        }
        comment.setContent(content);
        comment.setUserId(userId);
        comment.setType(type);
        BeanHelper.onInsert(comment);
        BeanHelper.setDefaultProp(comment, Comment.class);
        commentMapper.insert(comment);
    }

    public List<Comment> getHouseComments(Long houseId, int size) {
        List<Comment> comments = commentMapper.selectComments(houseId,size);
        if (CollectionUtils.isNotEmpty(comments)) {
            comments.forEach(comment -> {
                User user = userService.getUserById(comment.getUserId());
                BeanHelper.onUpdate(comment);
                BeanHelper.onInsert(comment);
                BeanHelper.setDefaultProp(comment,Comment.class);
                comment.setAvatar(user.getAvatar());
                comment.setUserName(user.getName());
            });
            return comments;
        }
        return Lists.newArrayList();
    }

    public List<Comment> getBlogComments(long blogId, int size) {
        List<Comment> comments = commentMapper.selectBlogComments(blogId,size);
        comments.forEach(comment -> {
            User user = userService.getUserById(comment.getUserId());
            comment.setUserName(user.getName());
            comment.setAvatar(user.getAvatar());
        });
        return comments;
    }

    public void addBlogComment(int blogId, String content, Long userId) {
        addComment(null,blogId, content, userId, CommonConstants.COMMENT_BLOG_TYPE);
    }

}
