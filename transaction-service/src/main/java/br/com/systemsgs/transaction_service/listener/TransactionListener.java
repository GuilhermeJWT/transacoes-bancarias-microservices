package br.com.systemsgs.transaction_service.listener;

import br.com.systemsgs.transaction_service.dto.PayloadTransacaoRequestRabbitMq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.messaging.Message;

import static br.com.systemsgs.transaction_service.config.RabbitMqConfiguration.QUEUE_TRANSACTION;

@Slf4j
@Component
public class TransactionListener {

    @RabbitListener(queues = QUEUE_TRANSACTION)
    public void dadosPedidoTransactionListener(Message<PayloadTransacaoRequestRabbitMq> payloadMessage){
        try{
            log.info("Mensagem consumida {}" ,payloadMessage);
            System.out.println(payloadMessage.toString());

        }catch (Exception e){
            log.error("Erro ao tentar receber a mensagem {}", e);
        }
    }
}
