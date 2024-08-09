package br.com.systemsgs.userservice.service.impl;

import br.com.systemsgs.userservice.DadosEstaticosEntidades;
import br.com.systemsgs.userservice.dto.payloads.PayloadResponseTransactionAprovada;
import br.com.systemsgs.userservice.dto.payloads.PayloadTransacaoRequestRabbitMq;
import br.com.systemsgs.userservice.dto.request.PedidoTransacaoDTO;
import br.com.systemsgs.userservice.model.ModelUsuarios;
import br.com.systemsgs.userservice.repository.UsuariosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;


@ActiveProfiles(value = "test")
@SpringBootTest
class PedidoTransacaoServiceImplTest extends DadosEstaticosEntidades {

    private ModelUsuarios modelUsuarios;
    private Optional<ModelUsuarios> modelUsuariosOptional;
    private PedidoTransacaoDTO pedidoTransacaoDTO;
    private PayloadTransacaoRequestRabbitMq payloadTransacaoRequestRabbitMq;
    private PayloadResponseTransactionAprovada payloadResponseTransactionAprovada;

    @InjectMocks
    private PedidoTransacaoServiceImpl pedidoTransacaoService;

    @Mock
    private UsuariosRepository usuariosRepository;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startTransacao();
    }

    @DisplayName("Teste para o Pedido de Transação")
    @Test
    void testPedidoTransacao() {
        when(usuariosRepository.findById(anyLong())).thenReturn(modelUsuariosOptional);
    }

    @DisplayName("Teste prepara Payload - RabbitMq")
    @Test
    void testConfigurarPayloadRabbitMq() {

        PayloadTransacaoRequestRabbitMq response = pedidoTransacaoService.
                dadosPedidoTransacao(modelUsuarios, modelUsuarios, pedidoTransacaoDTO);

        assertEquals(dadosUsuarios().getId(), response.getIdPagador());
        assertEquals(dadosUsuarios().getNome(), response.getNomePagador());
        assertEquals(dadosUsuarios().getId(), response.getIdBeneficiario());
        assertEquals(dadosUsuarios().getNome(), response.getNomeBeneficiario());
        assertEquals(dadosUsuarios().getEmail(), response.getEmailBeneficiario());
        assertEquals(dadosUsuarios().getTipoCarteira().name(), response.getTipoCarteira());
        assertEquals(dadosPayloadRequestTransaction().getValorTransferencia(), response.getValorTransferencia());
    }

    private void startTransacao(){
        modelUsuarios = new ModelUsuarios(
                dadosUsuarios().getId(),
                dadosUsuarios().getNome(),
                dadosUsuarios().getEmail(),
                dadosUsuarios().getCpf(),
                dadosUsuarios().getValorConta(),
                dadosUsuarios().getTipoCarteira(),
                dadosUsuarios().getDataCriacao(),
                dadosUsuarios().getDataAlteracao()
        );
        modelUsuariosOptional = Optional.of(new ModelUsuarios(
                dadosUsuarios().getId(),
                dadosUsuarios().getNome(),
                dadosUsuarios().getEmail(),
                dadosUsuarios().getCpf(),
                dadosUsuarios().getValorConta(),
                dadosUsuarios().getTipoCarteira(),
                dadosUsuarios().getDataCriacao(),
                dadosUsuarios().getDataAlteracao()
        ));
        pedidoTransacaoDTO = new PedidoTransacaoDTO(
                dadosPedidoTransacao().getIdPagador(),
                dadosPedidoTransacao().getIdBeneficiario(),
                dadosPedidoTransacao().getValorTransferencia()
        );
        payloadTransacaoRequestRabbitMq = new PayloadTransacaoRequestRabbitMq(
                dadosPayloadRequestTransaction().getIdPagador(),
                dadosPayloadRequestTransaction().getNomePagador(),
                dadosPayloadRequestTransaction().getIdBeneficiario(),
                dadosPayloadRequestTransaction().getNomeBeneficiario(),
                dadosPayloadRequestTransaction().getEmailBeneficiario(),
                dadosPayloadRequestTransaction().getValorTransferencia(),
                dadosPayloadRequestTransaction().getTipoCarteira()
        );
        payloadResponseTransactionAprovada = new PayloadResponseTransactionAprovada(
                dadosTransacionAprovada().getIdPagador(),
                dadosTransacionAprovada().getIdBeneficiario(),
                dadosTransacionAprovada().getValorTransferencia(),
                dadosTransacionAprovada().getStatusTransacao()
        );
    }
}