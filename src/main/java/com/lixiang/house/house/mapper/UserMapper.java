package com.lixiang.house.house.mapper;

import com.lixiang.house.house.common.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lixiang1234_李祥
 * @site www.lixiang.com
 * @create 2020-03-08 11:46
 */
@Mapper
@Repository
public interface UserMapper {

    List<User> selectUsers();

    int insert(User account);

    int delete(String email);

    int update(User updateUser);

    List<User> selectUsersByQuery(User user);
}
