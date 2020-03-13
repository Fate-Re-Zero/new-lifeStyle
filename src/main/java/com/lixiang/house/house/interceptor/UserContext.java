package com.lixiang.house.house.interceptor;

import com.lixiang.house.house.common.model.User;

/**
 * @author lixiang1234_李祥
 * @site www.lixiang.com
 * @create 2020-03-10 10:14
 */
public class UserContext {

    private static final ThreadLocal<User> USER_HOLDER = new ThreadLocal<>();

    public static void setUser(User user){
        USER_HOLDER.set(user);
    }

    public static void remove(){
        USER_HOLDER.remove();
    }

    public static User getUser(){
        return USER_HOLDER.get();
    }
}
