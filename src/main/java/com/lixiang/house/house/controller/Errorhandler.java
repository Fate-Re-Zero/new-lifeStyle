package com.lixiang.house.house.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常统一处理的类，那么这个类在Spring中可以用@ControllerAdvice来实现
 * @ControllerAdvice注解一般用作处理系统error，拦截出错信息，返回报错提示界面，防止用户看到一推出错信息！
 * @author lixiang1234_李祥
 * @site www.lixiang.com
 * @create 2020-03-09 8:39
 */
@ControllerAdvice //Controller辅助类
public class Errorhandler {

    /**
     * 1、通过@ControllerAdvice注解可以将对于控制器的全局配置放在同一个位置。
     * 2、注解了@Controller的类的方法可以使用@ExceptionHandler、@InitBinder、@ModelAttribute注解到方法上。
     * 3、@ControllerAdvice注解将作用在所有注解了@RequestMapping的控制器的方法上。
     * 4、@ExceptionHandler：用于全局处理控制器里的异常。定义出现那些异常时触发errorhandler
     * 5、@InitBinder：用来设置WebDataBinder，用于自动绑定前台请求参数到Model中。
     * 6、@ModelAttribute：本来作用是绑定键值对到Model中，此处让全局的@RequestMapping都能获得在此处设置的键值对
     */
    private static final Logger logger = LoggerFactory.getLogger(Errorhandler.class);

    @ExceptionHandler(value = {Exception.class,RuntimeException.class})
    public String error500(HttpServletRequest request,Exception e){

        logger.error(e.getMessage(),e);
        logger.error(request.getRequestURL()+" encounter 500");
        return "error/500";
    }


}
