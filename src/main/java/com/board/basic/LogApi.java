package com.board.basic;

import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//@Slf4j
@Component
public class LogApi {
//    private final Logger log = LoggerFactory.getLogger(LogApi.class);

    public void WriteLog(String textLog) {
//        log.info("LOG : " + textLog);
//        log.info("로그 테스트");
    }
}
