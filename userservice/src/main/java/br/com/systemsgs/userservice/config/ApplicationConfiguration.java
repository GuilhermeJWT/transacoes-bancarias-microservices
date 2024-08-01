package br.com.systemsgs.userservice.config;

import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    public static final String QUEUE_TRANSACATION = "queue-transaction";

    @Bean
    public ModelMapper mapper(){
        return new ModelMapper();
    }

    @Bean
    public Declarable transactionQueue(){
        return new Queue(QUEUE_TRANSACATION);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
