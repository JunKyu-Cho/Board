package com.board.basic.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class UserFilter implements Filter {
    private static Logger log = LogManager.getLogger(UserFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("== doFilter start ==");
        chain.doFilter(new RequestWrapper((HttpServletRequest) request), response);
        log.info("== doFilter end ==");
    }
}
