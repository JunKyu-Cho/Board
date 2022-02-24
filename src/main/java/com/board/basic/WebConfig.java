package com.board.basic;

import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/summernoteImage/**")          // /summernoteImage/ => 이 경로를 적으면
                .addResourceLocations("file:///c:/temp/");                      // file:///c:/temp/ => 이 경로로 적용
    }
}
