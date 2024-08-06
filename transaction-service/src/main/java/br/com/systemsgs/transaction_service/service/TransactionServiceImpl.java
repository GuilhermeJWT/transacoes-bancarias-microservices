package br.com.systemsgs.transaction_service.service;

import br.com.systemsgs.transaction_service.dto.PayloadTransacaoRequestRabbitMq;
import br.com.systemsgs.transaction_service.enums.TipoCarteira;
import br.com.systemsgs.transaction_service.exception.erros.TransacaoNegadaException;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl {

    public void processaValidacaoPedidoTransacao(PayloadTransacaoRequestRabbitMq payloadMessage){

        if(!payloadMessage.getTipoCarteira().equals(TipoCarteira.USUARIO_COMUM)) {
             System.out.println("deu certo");
        }else {
            throw new TransacaoNegadaException();
        }
    }
}
