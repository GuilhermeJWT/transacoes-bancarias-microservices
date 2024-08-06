package br.com.systemsgs.transaction_service.service;

import br.com.systemsgs.transaction_service.dto.PayloadTransacaoRequestRabbitMq;
import br.com.systemsgs.transaction_service.enums.StatusPedidoTransacao;
import br.com.systemsgs.transaction_service.enums.TipoCarteira;
import br.com.systemsgs.transaction_service.exception.erros.TransacaoNegadaException;
import br.com.systemsgs.transaction_service.model.ModelTransaction;
import br.com.systemsgs.transaction_service.repository.TransactionRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TransactionServiceImpl {

    private final TransactionRepository transactionRepository;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, RabbitTemplate rabbitTemplate) {
        this.transactionRepository = transactionRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Transactional
    public void processaValidacaoPedidoTransacao(PayloadTransacaoRequestRabbitMq payloadMessage){
        if(!payloadMessage.getTipoCarteira().equals(TipoCarteira.USUARIO_COMUM)) {

            var transacaoSalva =  salvaTransacaoAprovada(payloadMessage);

        }else {
            throw new TransacaoNegadaException();
        }
    }

    private ModelTransaction salvaTransacaoAprovada(PayloadTransacaoRequestRabbitMq payloadMessage){
        ModelTransaction modelTransaction = new ModelTransaction();

        modelTransaction.setIdTransacao(UUID.randomUUID());
        modelTransaction.setIdPagador(payloadMessage.getIdPagador());
        modelTransaction.setNomePagador(payloadMessage.getNomePagador());
        modelTransaction.setIdBeneficiario(payloadMessage.getIdBeneficiario());
        modelTransaction.setNomeBeneficiario(payloadMessage.getNomeBeneficiario());
        modelTransaction.setEmailBeneficiario(payloadMessage.getEmailBeneficiario());
        modelTransaction.setValorTransferencia(payloadMessage.getValorTransferencia());
        modelTransaction.setTipoCarteira(StatusPedidoTransacao.AUTORIZADA.name());

        return transactionRepository.save(modelTransaction);
    }

}
