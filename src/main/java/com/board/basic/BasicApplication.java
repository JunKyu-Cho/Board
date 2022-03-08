package com.board.basic;

import com.board.basic.controller.BoardController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class BasicApplication {
	private static Logger log = LogManager.getLogger(BasicApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(BasicApplication.class, args);
	}
}
