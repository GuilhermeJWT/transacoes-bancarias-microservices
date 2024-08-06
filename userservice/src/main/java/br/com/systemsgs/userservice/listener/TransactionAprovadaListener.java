package br.com.systemsgs.userservice.listener;

import br.com.systemsgs.userservice.dto.payloads.PayloadResponseTransactionAprovada;
import br.com.systemsgs.userservice.service.PedidoTransacaoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.messaging.Message;

import static br.com.systemsgs.userservice.config.RabbitMqConfiguration.QUEUE_TRANSACTION_APROVADA;

@Component
public class TransactionAprovadaListener {

    private final PedidoTransacaoService pedidoTransacaoService;

    @Autowired
    public TransactionAprovadaListener(PedidoTransacaoService pedidoTransacaoService) {
        this.pedidoTransacaoService = pedidoTransacaoService;
    }

    @RabbitListener(queues = QUEUE_TRANSACTION_APROVADA)
    public void pegaResultadoPedidoTransacaoAprovada(Message<PayloadResponseTransactionAprovada> payloadMessage){
        pedidoTransacaoService.dadosPedidoTransacaoAprovada(payloadMessage.getPayload());
    }
}
