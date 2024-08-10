package br.com.systemsgs.transactionservice.service;

import br.com.systemsgs.transactionservice.DadosEstaticosEntidades;
import br.com.systemsgs.transactionservice.dto.PayloadNotificationTransaction;
import br.com.systemsgs.transactionservice.dto.PayloadResponseTransactionAprovada;
import br.com.systemsgs.transactionservice.dto.PayloadTransacaoRequestRabbitMq;
import br.com.systemsgs.transactionservice.enums.TipoCarteira;
import br.com.systemsgs.transactionservice.model.ModelTransaction;
import br.com.systemsgs.transactionservice.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ActiveProfiles(value = "test")
@SpringBootTest
class TransactionServiceImplTest extends DadosEstaticosEntidades {

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessaValidacaoPedidoTransacaoException() {
        PayloadTransacaoRequestRabbitMq payloadMessage = dadosPayloadTransacaoRequestRabbitMq();

        when(transactionService.salvaTransacaoAprovada(payloadMessage)).thenThrow(new RuntimeException("Erro simulado"));

        assertThrows(RuntimeException.class, () -> {
            transactionService.processaValidacaoPedidoTransacao(payloadMessage);
        });

        verify(rabbitTemplate, never()).convertAndSend(anyString(), Optional.ofNullable(any()));
    }


    @Test
    void testProcessaValidacaoPedidoTransacaoNegada() {
        PayloadTransacaoRequestRabbitMq payload = new PayloadTransacaoRequestRabbitMq();
        payload.setTipoCarteira(TipoCarteira.LOJISTA.name());

        assertThrowsExactly(NullPointerException.class, () -> {
            transactionService.processaValidacaoPedidoTransacao(payload);
        });

        verify(transactionRepository, never()).save(any(ModelTransaction.class));
        verify(rabbitTemplate, never()).convertAndSend(anyString(), Optional.ofNullable(any()));
    }

    @Test
    void testDadosNotificacao() {
        ModelTransaction transactionRequest = new ModelTransaction();

        transactionRequest.setIdTransacao(dadosTransaction().getIdTransacao());
        transactionRequest.setIdPagador(dadosTransaction().getIdPagador());
        transactionRequest.setNomePagador(dadosTransaction().getNomePagador());
        transactionRequest.setIdBeneficiario(dadosTransaction().getIdBeneficiario());
        transactionRequest.setNomeBeneficiario(dadosTransaction().getNomeBeneficiario());
        transactionRequest.setEmailBeneficiario(dadosTransaction().getEmailBeneficiario());
        transactionRequest.setValorTransferencia(dadosTransaction().getValorTransferencia());
        transactionRequest.setTipoCarteira(dadosTransaction().getTipoCarteira());
        transactionRequest.setStatusTransacao(dadosTransaction().getStatusTransacao());
        transactionRequest.setDataHoraTransacao(dadosTransaction().getDataHoraTransacao());

        PayloadNotificationTransaction response = transactionService.dadosNotificacao(transactionRequest);

        assertEquals(transactionRequest.getIdTransacao(), response.getIdTransacao());
        assertEquals(transactionRequest.getNomePagador(), response.getNomePagador());
        assertEquals(transactionRequest.getNomeBeneficiario(), response.getNomeBeneficiario());
        assertEquals(transactionRequest.getEmailBeneficiario(), response.getEmailBeneficiario());
        assertEquals(transactionRequest.getValorTransferencia(), response.getValorTransferencia());
        assertEquals(transactionRequest.getDataHoraTransacao(), response.getDataHoraTransacao());
    }

    @Test
    void testConverteDadosTransacaoAprovada() {
        ModelTransaction transaction = dadosTransaction();

        PayloadResponseTransactionAprovada result = transactionService.converteDadosTransacaoAprovada(transaction);

        assertEquals(transaction.getIdPagador(), result.getIdPagador());
        assertEquals(transaction.getIdBeneficiario(), result.getIdBeneficiario());
        assertEquals(transaction.getValorTransferencia(), result.getValorTransferencia());
        assertEquals(transaction.getStatusTransacao(), result.getStatusTransacao());
    }

    @Test
    void testFormataDataHoraTransacao() {
        String result = transactionService.formataDataHoraTransacao();

        assertNotNull(result);
        assertTrue(result.matches("\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}:\\d{2}"));
    }


    @DisplayName("Formata Data e Hora e converte para String")
    @Test
    void formataDataHoraTransacao() {
        LocalDateTime dataHoraSimulada = LocalDateTime.of(2024, 8, 9, 23, 24, 45);

        DateTimeFormatter formataDataHora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataHoraEsperada = formataDataHora.format(dataHoraSimulada);

        try (MockedStatic<LocalDateTime> mock = Mockito.mockStatic(LocalDateTime.class)) {
            mock.when(LocalDateTime::now).thenReturn(dataHoraSimulada);

            String resultado = transactionService.formataDataHoraTransacao();

            assertEquals(dataHoraEsperada, resultado);
        }
    }

    @Test
    void testProcessaValidacaoPedidoTransacaoTipoCarteiraComum() {
        PayloadTransacaoRequestRabbitMq payloadMessage = mock(PayloadTransacaoRequestRabbitMq.class);
        when(payloadMessage.getTipoCarteira()).thenReturn(TipoCarteira.USUARIO_COMUM.name());

        assertThrows(NullPointerException.class, () -> {
            transactionService.processaValidacaoPedidoTransacao(payloadMessage);
        });

        verify(rabbitTemplate, never()).convertAndSend(anyString(), Optional.ofNullable(any()));
    }
}