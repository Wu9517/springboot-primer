package com.wzy.springboot.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 为ws.html提供便捷的路径映射
 *
 * @author wzy
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/ws").setViewName("/ws");
        registry.addViewController("/login").setViewName("/login");
        registry.addViewController("/chat").setViewName("/chat");
        registry.addViewController("/action").setViewName("/action");
        registry.addViewController("/view1").setViewName("/view1");
        registry.addViewController("/view2").setViewName("/view2");
    }
}
