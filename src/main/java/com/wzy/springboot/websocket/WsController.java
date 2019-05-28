package com.wzy.springboot.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * @author wzy
 */
@Controller
public class WsController {

    //通过simpMessagingTemplate向浏览器发送消息
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

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

    @MessageMapping("/chat")
    public void handleChat(Principal principal, String msg) {
        //在SpringMVC中，可以直接在参数中获的principal，principle里包含当前用户的信息
        if (principal.getName().equals("wzy")) {
            simpMessagingTemplate.convertAndSendToUser("zpc",
                    "/queue/notifications", principal.getName() + "-send:" + msg);
        } else {
            simpMessagingTemplate.convertAndSendToUser("wzy",
                    "/queue/notifications", principal.getName() + "-send:" + msg);
        }
    }


}
