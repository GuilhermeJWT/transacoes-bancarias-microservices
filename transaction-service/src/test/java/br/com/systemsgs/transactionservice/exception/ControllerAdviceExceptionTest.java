package br.com.systemsgs.transactionservice.exception;

import br.com.systemsgs.transactionservice.DadosEstaticosEntidades;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(value = "test")
@SpringBootTest
class ControllerAdviceExceptionTest extends DadosEstaticosEntidades {

    @InjectMocks
    private ControllerAdviceException controllerAdviceException;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Retorna Exception - Recurso não Encontrado! - 404")
    @Test
    void valorTransacaoMaiorContaAtualException() {
        ApiRestErrors apiRestErrors = controllerAdviceException.
                valorTransacaoMaiorContaAtualException();

        retornaAssertException(apiRestErrors);
    }

    @DisplayName("Transação Negada - 422")
    @Test
    void testTransacaoNegadaException() {
        ApiRestErrors apiRestErrors = controllerAdviceException.
                transacaoNegadaException();

        retornaAssertException(apiRestErrors);
    }

    @DisplayName("Erro Interno no Servidor - 500")
    @Test
    void testInternalServerErroException500() {
        ApiRestErrors apiRestErrors = controllerAdviceException.
                internalServerErroException();

        retornaAssertException(apiRestErrors);
    }

    @DisplayName("Erro ao tentar converter a mensagem - RabbitMQ! - 500")
    @Test
    void testErroConverterMensagemRabbitMqException500() {
        ApiRestErrors apiRestErrors = controllerAdviceException.
                erroConverterMensagemRabbitMqException();

        retornaAssertException(apiRestErrors);
    }

    private void retornaAssertException(ApiRestErrors apiRestErrors){
        assertNotNull(apiRestErrors);
        assertNotNull(apiRestErrors.getErros());
        assertEquals(ApiRestErrors.class, apiRestErrors.getClass());
    }

}