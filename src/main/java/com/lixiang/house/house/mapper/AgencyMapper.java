package com.lixiang.house.house.mapper;

import com.lixiang.house.house.common.model.Agency;
import com.lixiang.house.house.common.model.User;
import com.lixiang.house.house.common.page.PageParams;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lixiang1234_李祥
 * @site www.lixiang.com
 * @create 2020-03-11 9:52
 */
@Mapper
@Repository
public interface AgencyMapper {

    List<User> selectAgent(@Param("user") User user,@Param("pageParams") PageParams pageParams);

    Long selectAgentCount(@Param("user") User user);

    List<Agency> select(Agency agency);

    int insert(Agency agency);
}
