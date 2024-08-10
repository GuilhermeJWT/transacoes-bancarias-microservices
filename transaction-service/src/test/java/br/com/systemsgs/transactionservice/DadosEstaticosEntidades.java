package br.com.systemsgs.transactionservice;

import br.com.systemsgs.transactionservice.dto.PayloadNotificationTransaction;
import br.com.systemsgs.transactionservice.dto.PayloadResponseTransactionAprovada;
import br.com.systemsgs.transactionservice.dto.PayloadTransacaoRequestRabbitMq;
import br.com.systemsgs.transactionservice.enums.StatusPedidoTransacao;
import br.com.systemsgs.transactionservice.model.ModelTransaction;
import lombok.Getter;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ActiveProfiles(value = "test")
@Getter
public class DadosEstaticosEntidades {

    public List<String> mensagemErro(){
        List<String> msgErro = new ArrayList<>();

        String erroConverterMsgRabbitMq = "Erro ao tentar converter a mensagem - RabbitMQ!";
        String erroInternoServidor = "Erro Interno no Servidor, Contate a equipe de Desenvolvimento.";
        String erroProcessarDados = "Erro ao processar os dados da Transação!";
        String erroTransacaoNegada = "Transação Negada! apenas Usuários Comuns podem Transferir Dinheiro.";

        msgErro.add(erroConverterMsgRabbitMq);
        msgErro.add(erroInternoServidor);
        msgErro.add(erroProcessarDados);
        msgErro.add(erroTransacaoNegada);

        return msgErro;
    }

    public ModelTransaction dadosTransaction(){
        ModelTransaction transactionResponse = new ModelTransaction();

        transactionResponse.setIdTransacao(UUID.fromString("0b7b5247-c3a9-44f9-b17a-1ffb10a13580"));
        transactionResponse.setIdPagador(1L);
        transactionResponse.setNomePagador("Guilherme Santos");
        transactionResponse.setIdBeneficiario(2l);
        transactionResponse.setNomeBeneficiario("Guilherme Beneficiário");
        transactionResponse.setEmailBeneficiario("beneficiario@gmail.com");
        transactionResponse.setValorTransferencia(new BigDecimal(1000.00));
        transactionResponse.setTipoCarteira("USUARIO_COMUM");
        transactionResponse.setStatusTransacao(StatusPedidoTransacao.AUTORIZADA);
        transactionResponse.setDataHoraTransacao("09/08/2024 22:26:54");

        return transactionResponse;
    }

    public PayloadNotificationTransaction dadosPayloadNotificationTransaction(){
        PayloadNotificationTransaction payloadResponse = new PayloadNotificationTransaction();


        payloadResponse.setIdTransacao(UUID.fromString("0b7b5247-c3a8-44f9-b17a-1ffb10a13580"));
        payloadResponse.setNomePagador("Guilherme Santos");
        payloadResponse.setNomeBeneficiario("Guilherme Beneficiário");
        payloadResponse.setEmailBeneficiario("beneficiario@gmail.com");
        payloadResponse.setValorTransferencia(new BigDecimal(1000.00));
        payloadResponse.setDataHoraTransacao("09/08/2024 23:30:54");

        return payloadResponse;
    }

    public PayloadResponseTransactionAprovada dadosPayloadTransacaoAprovada(){
        PayloadResponseTransactionAprovada payloadResponse = new PayloadResponseTransactionAprovada();

        payloadResponse.setIdPagador(1L);
        payloadResponse.setIdBeneficiario(2L);
        payloadResponse.setValorTransferencia(new BigDecimal(1000.00));
        payloadResponse.setStatusTransacao(StatusPedidoTransacao.AUTORIZADA);

        return payloadResponse;
    }

    public PayloadTransacaoRequestRabbitMq dadosPayloadTransacaoRequestRabbitMq(){
        PayloadTransacaoRequestRabbitMq payloadResponse = new PayloadTransacaoRequestRabbitMq();

        payloadResponse.setIdPagador(1L);
        payloadResponse.setNomePagador("Guilherme Santos");
        payloadResponse.setIdBeneficiario(2L);
        payloadResponse.setNomeBeneficiario("Guilherme Beneficiário");
        payloadResponse.setEmailBeneficiario("beneficiario@gmail.com");
        payloadResponse.setValorTransferencia(new BigDecimal(1000.00));
        payloadResponse.setTipoCarteira("USUARIO_COMUM");

        return payloadResponse;
    }
}
