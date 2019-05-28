package com.wzy.springboot.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * @author wzy
 */
@Controller
public class WsController {

    @MessageMapping("/welcome") //当浏览器向服务端发送请求时，通过@MessageMapping映射/welcome这个地，类似于@RequestMapping
    @SendTo("/topic/getResponse") //当服务端有消息时，会对订阅了@SendTo中的路径的浏览器发送消息
    public WsResponse say(WsMessage message) {
        try {
            Thread.sleep(3000);
            return new WsResponse("Welcome, " + message.getName() + "!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
