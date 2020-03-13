package com.lixiang.house.house.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author lixiang1234_李祥
 * @site www.lixiang.com
 * @create 2020-03-08 10:44
 */
@WebFilter("/*")
public class LogFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(Logger.class);

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
//        logger.info("Request->coming");
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

    public void destroy() {
    }
}
