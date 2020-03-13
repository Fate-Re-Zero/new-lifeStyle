package com.lixiang.house.house.common.constants;

/**
 * @author lixiang1234_李祥
 * @site www.lixiang.com
 * @create 2020-03-12 9:45
 */
public enum HouseUserType {

    SALE(1),BOOKMARK(2);

    public final Integer value;


    private HouseUserType(Integer value){
        this.value = value;
    }
}
