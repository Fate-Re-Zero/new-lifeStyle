package com.lixiang.house.house.controller;

import com.google.common.base.Objects;
import com.lixiang.house.house.common.model.User;
import com.lixiang.house.house.common.result.ResultMsg;

import org.apache.commons.lang3.StringUtils;

/**
 * @author lixiang1234_李祥
 * @site www.lixiang.com
 * @create 2020-03-09 10:21
 */
public class UserHelper {

    public static ResultMsg validate(User account){

        if (StringUtils.isBlank(account.getEmail())){
            return ResultMsg.errorMsg("Email 有误！");
        }
        if (StringUtils.isBlank(account.getName())){
            return ResultMsg.errorMsg("用户名有误！");
        }
        if (StringUtils.isBlank(account.getPasswd())){
            return ResultMsg.errorMsg("密码不能为空");
        }
        if (StringUtils.isBlank(account.getConfirmPasswd())){
            return ResultMsg.errorMsg("确认密码不能为空");
        }
        if (!account.getPasswd().equals(account.getConfirmPasswd())){
            return ResultMsg.errorMsg("两次输入密码不一致！");
        }
        if (account.getPasswd().length() < 6){
            return ResultMsg.errorMsg("密码长度必须大于6位");
        }
        return ResultMsg.successMsg("");
    }

    public static ResultMsg validateResetPassword(String key, String password, String confirmPassword) {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(password) || StringUtils.isBlank(confirmPassword)) {
            return ResultMsg.errorMsg("参数有误");
        }
        if (!Objects.equal(password, confirmPassword)) {
            return ResultMsg.errorMsg("密码必须与确认密码一致");
        }
        return ResultMsg.successMsg("");
    }

}
