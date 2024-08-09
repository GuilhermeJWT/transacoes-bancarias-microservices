package br.com.systemsgs.userservice.controller;

import br.com.systemsgs.userservice.DadosEstaticosEntidades;
import br.com.systemsgs.userservice.dto.request.ModelUsuariosDTO;
import br.com.systemsgs.userservice.dto.response.UsuarioResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.config.JsonPathConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import static io.restassured.config.JsonConfig.jsonConfig;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(OrderAnnotation.class)
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles(value = "test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UsuarioControllerTest extends DadosEstaticosEntidades {

    private ModelUsuariosDTO modelUsuariosDTO;
    private UsuarioResponse usuarioResponse;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
        RestAssured.config = RestAssured.config().jsonConfig(jsonConfig().
                numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL));
        startUsuario();
    }

    @Order(1)
    @DisplayName("Teste para salvar um novo Usuário - 201")
    @Test
    void testSalvarUsuarioRetorna201() {

        given()
                .contentType(ContentType.JSON)
                .body(modelUsuariosDTO)
        .when()
                .post(URL_SALVAR_USUARIOS)
        .then()
                .statusCode(201)
                .header("Location", containsString("/salvar"))
                .body("codigo_usuario", notNullValue())
                .body("nome_usuario", equalTo(usuarioResponse.getNome()))
                .body("email", equalTo(usuarioResponse.getEmail()))
                .body("cpf", equalTo(usuarioResponse.getCpf()))
                .body("tipo_carteira", equalTo(usuarioResponse.getTipoCarteira()))
                .body("valor_na_conta", notNullValue())
                .body("data_criacao_do_usuario", notNullValue())
                .body("data_ultima_alteracao_do_usuario", notNullValue());
    }

    @Order(2)
    @DisplayName("Teste para listar Usuarios - 200")
    @Test
    void testeListarUsuarios200() {
        given()
                .contentType(ContentType.JSON)
        .when()
                .get(URL_LISTAR_USUARIOS)
        .then()
                .statusCode(200)
                .body("[0].codigo_usuario", notNullValue())
                .body("[0].nome_usuario", notNullValue())
                .body("[0].email", notNullValue())
                .body("[0].cpf", notNullValue())
                .body("[0].valor_na_conta", notNullValue())
                .body("[0].tipo_carteira", notNullValue())
                .body("[0].data_criacao_do_usuario", notNullValue())
                .body("[0].data_ultima_alteracao_do_usuario", notNullValue());
    }

    @Order(3)
    @DisplayName("Teste para Pesquisar um Usuario pelo ID - 200")
    @Test
    void testPesquisaUsuarioPorId200() {
        given()
                .contentType(ContentType.JSON)
        .when()
                .get(URL_PESQUISAR_USUARIO_POR_ID, usuarioResponse.getId())
        .then()
                .statusCode(200)
                .body("codigo_usuario", notNullValue())
                .body("nome_usuario", notNullValue())
                .body("email", notNullValue())
                .body("cpf", notNullValue())
                .body("valor_na_conta", notNullValue())
                .body("tipo_carteira", notNullValue())
                .body("data_criacao_do_usuario", notNullValue())
                .body("data_ultima_alteracao_do_usuario", notNullValue());
    }

    @Order(4)
    @DisplayName("Teste para Pesquisar um Usuario inexistente pelo ID - 404")
    @Test
    void testBuscarUsuarioPorIdNaoEncontrado404(){
        given()
                .contentType(ContentType.JSON)
        .when()
                .get(URL_PESQUISAR_USUARIO_POR_ID, 0)
        .then()
                .statusCode(404)
                .body("erros[0]", equalTo(mensagemErro().get(4)));
    }

    private void startUsuario(){
        usuarioResponse = new UsuarioResponse(
         dadosUsuarios().getId(),
         dadosUsuarios().getNome(),
         dadosUsuarios().getEmail(),
         dadosUsuarios().getCpf(),
         dadosUsuarios().getValorConta(),
         dadosUsuarios().getTipoCarteira().name(),
         dadosUsuarios().getDataCriacao(),
         dadosUsuarios().getDataAlteracao()
        );
        modelUsuariosDTO = new ModelUsuariosDTO(
          dadosUsuarios().getNome(),
          dadosUsuarios().getEmail(),
          dadosUsuarios().getCpf(),
          dadosUsuarios().getValorConta(),
          dadosUsuarios().getTipoCarteira()
        );
    }
}