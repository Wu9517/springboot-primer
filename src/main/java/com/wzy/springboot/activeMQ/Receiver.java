package com.wzy.springboot.activeMQ;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * message driven POJO
 *
 * @author wzy
 */
@Component
public class Receiver {

    //监听的目的地
    @JmsListener(destination = "my-destination")
    public void receiveMesssage(String message) {
        System.out.println("接受到：<" + message + ">");
    }

    @JmsListener(destination = "mailbox", containerFactory = "myFactory")
    public void receiveMessage(Email email) {
        System.out.println("Received <" + email + ">");
    }
}
