package com.lixiang.house.house.service;

import com.google.common.collect.Lists;
import com.lixiang.house.house.common.model.City;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lixiang1234_李祥
 * @site www.lixiang.com
 * @create 2020-03-12 8:24
 */
@Service
public class CityService {

    public List<City> getAllCitys(){
        City city = new City();
        city.setCityCode("110000");
        city.setCityName("北京市");
        city.setId(1);
        return Lists.newArrayList(city);
    }
}
