package com.wzy.springboot.rabbitMQ;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wzy
 */
@Configuration(value = "rabbitApplication")
public class Application {

    static final String topicExchangeName = "spring-boot-exchange";

    static final String queueName = "spring-boot";

    //creates an AMQP queue
    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    //creates a topic exchange
    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(topicExchangeName);
    }

    //binds AMQP queue and topic exchange together,
    // defining the behavior that occurs when RabbitTemplate publishes to an exchange.
    @Bean
    Binding binding(Queue queue, TopicExchange topicExchange) {
        //"foo.bar.#" means any message sent with
        // a routing key beginning with foo.bar. will be routed to the queue.
        return BindingBuilder.bind(queue).to(topicExchange).with("foo.bar.#");
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }


    //The method is registered as a message listener in the container defined in container()
    //It will listen for messages on the "spring-boot" queue.
    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
}
