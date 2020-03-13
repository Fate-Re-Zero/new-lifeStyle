package com.lixiang.house.house.mapper;

import com.lixiang.house.house.common.model.*;
import com.lixiang.house.house.common.page.PageParams;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lixiang1234_李祥
 * @site www.lixiang.com
 * @create 2020-03-10 14:57
 */
@Mapper
@Repository
public interface HouseMapper {

    List<House> selectPageHouses(@Param("house") House house, @Param("pageParams") PageParams pageParams);

    Long selectPageCount(@Param("house") House query);

    List<Community> selectCommunity(Community community);

    int insertUserMsg(UserMsg userMsg);

    HouseUser selectSaleHouseUser(Long houseId);

    int insert(House house);

    HouseUser selectHouseUser(@Param("userId") Long userId,@Param("id") Long houseId,
                              @Param("type") Integer type);

    int insertHouseUser(HouseUser houseUser);

    int updateHouse(House updateHouse);

    int deleteHouseUser(@Param("id") Long id,@Param("userId") Long userId,@Param("type") Integer value);

    int downHouse(Long id);

}
