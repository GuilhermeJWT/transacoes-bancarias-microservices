package br.com.systemsgs.userservice;

import br.com.systemsgs.userservice.dto.payloads.PayloadResponseTransactionAprovada;
import br.com.systemsgs.userservice.dto.payloads.PayloadTransacaoRequestRabbitMq;
import br.com.systemsgs.userservice.dto.request.PedidoTransacaoDTO;
import br.com.systemsgs.userservice.enums.TipoCarteira;
import br.com.systemsgs.userservice.model.ModelUsuarios;
import lombok.Getter;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@ActiveProfiles(value = "test")
@Getter
public class DadosEstaticosEntidades {

    protected static final String URL_SALVAR_USUARIOS = "api/v1/usuarios/salvar";
    protected static final String URL_LISTAR_USUARIOS = "api/v1/usuarios/listar";
    protected static final String URL_PESQUISAR_USUARIO_POR_ID = "api/v1/usuarios/pesquisar/{id}";

    public List<String> mensagemErro(){
        List<String> msgErro = new ArrayList<>();

        String beneficiarioNaoEncontrado = "Beneficiario não Encontrado!";
        String camposDuplicados = "Campos já cadastrados na base de dados, Por Favor, Informe outros!";
        String metodoHttpNaoSuportado = "Tipo de solicitação HTTP incorreta, reveja qual o tipo correto: 'GET' 'POST' 'PUT' 'DELETE' ou outro!";
        String payloadInexistente = "Payload da Requisição Inexistente, informe os campos Válidos!";
        String usuarioNaoEncontrado = "Usuário não Encontrado!";
        String valorTransacaoMaior = "Valor da Transação é maior que o Saldo Atual da Conta!";

        msgErro.add(beneficiarioNaoEncontrado);
        msgErro.add(camposDuplicados);
        msgErro.add(metodoHttpNaoSuportado);
        msgErro.add(payloadInexistente);
        msgErro.add(usuarioNaoEncontrado);
        msgErro.add(valorTransacaoMaior);

        return msgErro;
    }

    public ModelUsuarios dadosUsuarios(){
        ModelUsuarios modelUsuarios = new ModelUsuarios();

        modelUsuarios.setId(Long.valueOf(1));
        modelUsuarios.setNome("Guilherme Santos");
        modelUsuarios.setEmail("guilhermeteste123@gmail.com");
        modelUsuarios.setCpf("266.224.510-79"); //gerado no site: GERADOR DE CPF
        modelUsuarios.setValorConta(new BigDecimal(1000));
        modelUsuarios.setTipoCarteira(TipoCarteira.USUARIO_COMUM);
        modelUsuarios.setDataCriacao(LocalDateTime.of(2024, Month.AUGUST, 8, 12, 57, 48, 123456789));
        modelUsuarios.setDataAlteracao(LocalDateTime.of(2024, Month.AUGUST, 10, 12, 50, 48, 123456789));

        return modelUsuarios;
    }

    public PayloadResponseTransactionAprovada dadosTransacionAprovada(){
        PayloadResponseTransactionAprovada payloadTransaction = new PayloadResponseTransactionAprovada();

        payloadTransaction.setIdPagador(1L);
        payloadTransaction.setIdBeneficiario(2L);
        payloadTransaction.setValorTransferencia(BigDecimal.valueOf(350));
        payloadTransaction.setStatusTransacao("APROVADA");

        return payloadTransaction;
    }

    public PayloadTransacaoRequestRabbitMq dadosPayloadRequestTransaction(){
        PayloadTransacaoRequestRabbitMq payloadTransacaoRequest = new PayloadTransacaoRequestRabbitMq();

        payloadTransacaoRequest.setIdPagador(1L);
        payloadTransacaoRequest.setNomePagador("Guilherme Santos");
        payloadTransacaoRequest.setIdBeneficiario(2L);
        payloadTransacaoRequest.setNomeBeneficiario("Guilherme Beneficiário");
        payloadTransacaoRequest.setEmailBeneficiario("beneficiarioteste@gmail.com");
        payloadTransacaoRequest.setValorTransferencia(BigDecimal.valueOf(230));
        payloadTransacaoRequest.setTipoCarteira("USUARIO_COMUM");

        return payloadTransacaoRequest;
    }

    public PedidoTransacaoDTO dadosPedidoTransacao(){
        PedidoTransacaoDTO pedidoTransacao = new PedidoTransacaoDTO();

        pedidoTransacao.setIdPagador(1L);
        pedidoTransacao.setIdBeneficiario(2L);
        pedidoTransacao.setValorTransferencia(BigDecimal.valueOf(350));

        return pedidoTransacao;
    }
}
