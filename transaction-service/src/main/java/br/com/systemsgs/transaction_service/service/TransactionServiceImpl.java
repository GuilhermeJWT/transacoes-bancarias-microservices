package br.com.systemsgs.transaction_service.service;

import br.com.systemsgs.transaction_service.dto.PayloadNotificationTransaction;
import br.com.systemsgs.transaction_service.dto.PayloadResponseTransactionAprovada;
import br.com.systemsgs.transaction_service.dto.PayloadTransacaoRequestRabbitMq;
import br.com.systemsgs.transaction_service.enums.StatusPedidoTransacao;
import br.com.systemsgs.transaction_service.enums.TipoCarteira;
import br.com.systemsgs.transaction_service.exception.erros.TransacaoNegadaException;
import br.com.systemsgs.transaction_service.model.ModelTransaction;
import br.com.systemsgs.transaction_service.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static br.com.systemsgs.transaction_service.config.RabbitMqConfiguration.QUEUE_NOTIFICATION_BENEFICIARIO;
import static br.com.systemsgs.transaction_service.config.RabbitMqConfiguration.QUEUE_TRANSACTION_APROVADA;

@Slf4j
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
            var dadosTransacaoAprovada = converteDadosTransacaoAprovada(transacaoSalva);
            var notificaBeneficiario = dadosNotificacao(transacaoSalva);

            rabbitTemplate.convertAndSend(QUEUE_TRANSACTION_APROVADA, dadosTransacaoAprovada);
            rabbitTemplate.convertAndSend(QUEUE_NOTIFICATION_BENEFICIARIO, notificaBeneficiario);

        }else {
            log.warn("Tentativa para realizar uma Transação foi negada....");
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
        modelTransaction.setDataHoraTransacao(formataDataHoraTransacao());
        modelTransaction.setTipoCarteira(payloadMessage.getTipoCarteira());
        modelTransaction.setStatusTransacao(StatusPedidoTransacao.AUTORIZADA);

        return transactionRepository.save(modelTransaction);
    }

    private PayloadNotificationTransaction dadosNotificacao(ModelTransaction modelTransaction){
        PayloadNotificationTransaction notificationTransaction = new PayloadNotificationTransaction();

        notificationTransaction.setIdTransacao(modelTransaction.getIdTransacao());
        notificationTransaction.setNomePagador(modelTransaction.getNomePagador());
        notificationTransaction.setNomeBeneficiario(modelTransaction.getNomeBeneficiario());
        notificationTransaction.setEmailBeneficiario(modelTransaction.getEmailBeneficiario());
        notificationTransaction.setValorTransferencia(modelTransaction.getValorTransferencia());
        notificationTransaction.setDataHoraTransacao(modelTransaction.getDataHoraTransacao());

        return notificationTransaction;
    }

    private PayloadResponseTransactionAprovada converteDadosTransacaoAprovada(ModelTransaction modelTransaction){
        PayloadResponseTransactionAprovada payloadTransactionAprovada = new PayloadResponseTransactionAprovada();

        payloadTransactionAprovada.setIdPagador(modelTransaction.getIdPagador());
        payloadTransactionAprovada.setIdBeneficiario(modelTransaction.getIdBeneficiario());
        payloadTransactionAprovada.setValorTransferencia(modelTransaction.getValorTransferencia());
        payloadTransactionAprovada.setStatusTransacao(modelTransaction.getStatusTransacao());

        return payloadTransactionAprovada;
    }

    private String formataDataHoraTransacao(){
        LocalDateTime dataHora = LocalDateTime.now();
        DateTimeFormatter formataDataHora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        return formataDataHora.format(dataHora);
    }

}
