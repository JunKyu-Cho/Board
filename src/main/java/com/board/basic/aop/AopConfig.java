package com.board.basic.aop;

import com.board.basic.BasicApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AopConfig {
    private static Logger log = LogManager.getLogger(AopConfig.class);

    @Before("execution(public * com.board.basic.controller.*.*(..))")
    public void doSomethingBefore() {
        log.debug("== AOP Before ==");
    }

    @After("execution(public * com.board.basic.controller.*.*(..))")
    public void doSomethingAfter() {
        log.debug("== AOP After ==");
    }
}
