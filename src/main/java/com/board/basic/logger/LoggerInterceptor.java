package com.board.basic.logger;

import com.board.basic.BasicApplication;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoggerInterceptor implements HandlerInterceptor {
    private static Logger log = LogManager.getLogger(BasicApplication.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws Exception {

        log.debug("== Interceptor preHandle ==");
        log.debug("Request URI : " + request.getRequestURI());

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handle, ModelAndView modelAndView) throws Exception {

        log.debug("== Interceptor postHandle ==");
    }
}
