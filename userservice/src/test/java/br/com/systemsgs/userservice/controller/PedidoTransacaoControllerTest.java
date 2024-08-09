package br.com.systemsgs.userservice.controller;

import br.com.systemsgs.userservice.DadosEstaticosEntidades;
import br.com.systemsgs.userservice.dto.request.PedidoTransacaoDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.config.JsonPathConfig;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static io.restassured.RestAssured.given;
import static io.restassured.config.JsonConfig.jsonConfig;
import static org.hamcrest.Matchers.equalTo;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles(value = "test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PedidoTransacaoControllerTest extends DadosEstaticosEntidades {

    private PedidoTransacaoDTO pedidoTransacaoDTO;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
        RestAssured.config = RestAssured.config().jsonConfig(jsonConfig().
                numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL));
        startPedidoTransacao();
    }

    @Order(5)
    @DisplayName("Teste para retornar Usuário inexistente -  Pedido de Transação - 404")
    @Test
    void testPedidoTransacaoUsuarioInexistente404() {

        given()
                .contentType(ContentType.JSON)
                .body(pedidoTransacaoDTO)
                .when()
                .post(URL_PEDIDO_TRANSACAO)
                .then()
                .statusCode(404)
                .body("erros[0]", equalTo(mensagemErro().get(4)));

    }

    private void startPedidoTransacao(){
        pedidoTransacaoDTO = new PedidoTransacaoDTO(
           dadosPedidoTransacao().getIdPagador(),
           dadosPedidoTransacao().getIdBeneficiario(),
           dadosPedidoTransacao().getValorTransferencia()
        );
    }
}