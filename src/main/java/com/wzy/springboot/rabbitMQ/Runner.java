package com.wzy.springboot.rabbitMQ;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author wzy
 */
@Component
public class Runner implements CommandLineRunner {

    private RabbitTemplate template;

    private Receiver receiver;

    public Runner(RabbitTemplate template, Receiver receiver) {
        this.template = template;
        this.receiver = receiver;
    }

    @Override
    public void run(String... strings) throws Exception {
        System.out.println("Sending message...");
        template.convertAndSend(Application.topicExchangeName, "foo.bar.baz", "Hello from RabbitMQ!");
        receiver.getLatch().await(10000, TimeUnit.MICROSECONDS);
    }
}
