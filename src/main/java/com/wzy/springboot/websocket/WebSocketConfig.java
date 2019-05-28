package com.wzy.springboot.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author wzy
 */
@Configuration
@EnableWebSocketMessageBroker //开启使用STOMP协议来传输基于代理（message broker）的消息
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 注册STOMP协议的节点（endpoint），并映射指定的URL。
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //注册一个STOMP的endpoint,并指定使用SockJS协议。
        registry.addEndpoint("/endpointWs").setAllowedOrigins("*").withSockJS();
    }


    /***
     * 配置消息代理(message broker)
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //广播式配制一个/topic消息代理。
        registry.enableSimpleBroker("/topic");
    }

}
