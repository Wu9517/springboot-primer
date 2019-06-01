package com.wzy.springboot.rabbitMQ;

import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * @author wzy
 */
@Component(value = "rabbitReceiver")
public class Receiver {

    //表明是否收到消息，生产环境中不会执行这个类。指定计数的次数，只能被设置1次
    private CountDownLatch latch = new CountDownLatch(1);


    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
        //次数减1
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
