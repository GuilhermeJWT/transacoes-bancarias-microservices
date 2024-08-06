package br.com.systemsgs.transaction_service.config;

import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {

    public static final String QUEUE_TRANSACTION = "queue-transaction";

    @Bean
    public Declarable orderQueue(){
        return new Queue(QUEUE_TRANSACTION);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
