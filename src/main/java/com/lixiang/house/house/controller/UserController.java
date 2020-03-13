package com.lixiang.house.house.controller;

import com.lixiang.house.house.common.constants.CommonConstants;
import com.lixiang.house.house.common.model.User;
import com.lixiang.house.house.common.result.ResultMsg;
import com.lixiang.house.house.common.utils.HashUtils;
import com.lixiang.house.house.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author lixiang1234_李祥
 * @site www.lixiang.com
 * @create 2020-03-08 12:27
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

//    @GetMapping("/selectUsers")
//    public @ResponseBody List<User> selectUsers() {
//        return userService.getUsers();
//    }

    /**
     * 注册提交:1.注册验证 2 发送邮件 3验证失败重定向到注册页面 注册页获取:根据account对象为依据判断是否注册页获取请求
     * @param account
     * @param modelMap
     * @return
     */
    @RequestMapping("accounts/register")
    public String accountsRegister(User account, ModelMap modelMap){

        if (account==null || account.getName()==null){
            return "/user/accounts/register";
        }
        ResultMsg resultMsg = UserHelper.validate(account);
        if (resultMsg.isSuccess() && userService.addAccount(account)){
            modelMap.put("email",account.getEmail());
            return "/user/accounts/registerSubmit";
        }else {
            return "redirect:/user/accounts/register" + resultMsg.asUrlParams();
        }
    }

    @RequestMapping("accounts/verify")
    public String verify(String key){
        boolean result = userService.enable(key);
        if (result){
            return "redirect:/index?" + ResultMsg.successMsg("激活成功").asUrlParams();
        }else {
            return "redirect:/accounts/register?" + ResultMsg.errorMsg("激活失败，请确认链接是否过期").asUrlParams();
        }
    }

    //--------------登录流程--------------------
    /**
     * 登录接口
     */
    @RequestMapping("/accounts/signin")
    public String signin(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String target = request.getParameter("target");
        //登录页的请求
        if (username == null || password == null){
            request.setAttribute("target",target);
            return "/user/accounts/signin";
        }
        //不是登录页的请求
        //auth用户的验证操作
        /*
         * 验证成功，将用户存入session中
         * 验证失败，返回登录页
         */
        User user = userService.auth(username,password);
        if (user == null){
           return "redirect:/accounts/signin?" + "target=" + target +
                   "&username=" + username + "&" + ResultMsg.errorMsg("用户名或密码错误！").asUrlParams();
        }else {
            HttpSession session = request.getSession(true);
            session.setAttribute(CommonConstants.USER_ATTRIBUTE,user);
//            session.setAttribute(CommonConstants.PLAIN_USER_ATTRIBUTE,user);
            return StringUtils.isNoneBlank(target)? "redirect:" + target :"redirect:/index";
        }
    }

    /**
     * 登出操作
     * @param request
     * @return
     */
    @RequestMapping("accounts/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(true);
        session.invalidate();
        return "redirect:/index";
    }

    //----------个人信息页---------------

    /**
     * 1.能够提供页面信息
     * 2.更新用户信息
     * @param updateUser
     * @param modelMap
     * @return
     */
    @RequestMapping("accounts/profile")
    public String profile(HttpServletRequest request,User updateUser,ModelMap modelMap){
        //email的属性值是一个隐藏的input，每次在更新的时候都会传递过来
        //如果email为空的话，就说明是一个个人信息页的请求
        if (updateUser.getEmail() == null){
            return "/user/accounts/profile";
        }
        //email设置为一个唯一索引,不用担心重复的问题
        userService.updateUser(updateUser,updateUser.getEmail());
        User query = new User();
        query.setEmail(updateUser.getEmail());
        List<User> users = userService.getUserByQuery(query);
        request.getSession(true).setAttribute(CommonConstants.USER_ATTRIBUTE,users.get(0));
        return "redirect:/accounts/profile?" + ResultMsg.successMsg("更新成功").asUrlParams();
    }

    /**
     * 修改密码操作
     * @param email
     * @param password
     * @param newPassword
     * @param confirmPassword
     * @param modelMap
     * @return
     */
    @RequestMapping("accounts/changePassword")
    public String changePassword(String email,String password,String newPassword,String confirmPassword,
                                 ModelMap modelMap){
        User user = userService.auth(email, password);
        if (user == null || !confirmPassword.equals(newPassword)){
            return "redirect:/accounts/profile?"+ResultMsg.errorMsg("密码错误").asUrlParams();
        }
        User updateUser = new User();
        updateUser.setPasswd(HashUtils.encryPassword(newPassword));;
        userService.updateUser(updateUser,updateUser.getEmail());
        return "redirect:/accounts/profile?" + ResultMsg.successMsg("更新成功").asUrlParams();
    }


    /**
     * 忘记密码
     * @param username
     * @param modelMap
     * @return
     */
    @RequestMapping("accounts/remember")
    public String remember(String username, ModelMap modelMap) {
        if (StringUtils.isBlank(username)) {
            return "redirect:/accounts/signin?" + ResultMsg.errorMsg("邮箱不能为空").asUrlParams();
        }
        userService.resetNotify(username);
        modelMap.put("email", username);
        return "/user/accounts/remember";
    }

    @RequestMapping("accounts/reset")
    public String reset(String key,ModelMap modelMap){
        String email = userService.getResetEmail(key);
        if (StringUtils.isBlank(email)) {
            return "redirect:/accounts/signin?" + ResultMsg.errorMsg("重置链接已过期").asUrlParams();
        }
        modelMap.put("email", email);
        modelMap.put("success_key", key);
        return "/user/accounts/reset";
    }

    @RequestMapping(value="accounts/resetSubmit")
    public String resetSubmit(HttpServletRequest req,User user){
        ResultMsg retMsg = UserHelper.validateResetPassword(user.getKey(), user.getPasswd(), user.getConfirmPasswd());
        if (!retMsg.isSuccess() ) {
            String suffix = "";
            if (StringUtils.isNotBlank(user.getKey())) {
                suffix = "email=" + userService.getResetEmail(user.getKey()) + "&key=" +  user.getKey() + "&";
            }
            return "redirect:/accounts/reset?"+ suffix  + retMsg.asUrlParams();
        }
        User updatedUser =  userService.reset(user.getKey(),user.getPasswd());
        req.getSession(true).setAttribute(CommonConstants.USER_ATTRIBUTE, updatedUser);
        return "redirect:/index?" + retMsg.asUrlParams();
    }



}
