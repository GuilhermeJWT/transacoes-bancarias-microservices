package br.com.systemsgs.userservice.controller;

import br.com.systemsgs.userservice.DadosEstaticosEntidades;
import br.com.systemsgs.userservice.dto.request.ModelUsuariosDTO;
import br.com.systemsgs.userservice.dto.response.UsuarioResponse;
import br.com.systemsgs.userservice.model.ModelUsuarios;
import br.com.systemsgs.userservice.service.impl.UsuarioServiceImpl;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.config.JsonPathConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.config.JsonConfig.jsonConfig;
import static org.junit.jupiter.api.Assertions.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@ActiveProfiles(value = "test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UsuarioControllerTest extends DadosEstaticosEntidades {

    private ModelUsuarios modelUsuarios;
    private ModelUsuariosDTO modelUsuariosDTO;
    private UsuarioResponse usuarioResponse;

    private final UsuarioServiceImpl usuarioService;
    private final ModelMapper mapper;

    @Autowired
    public UsuarioControllerTest(UsuarioServiceImpl usuarioService, ModelMapper mapper) {
        this.usuarioService = usuarioService;
        this.mapper = mapper;
    }

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
        RestAssured.config = RestAssured.config().jsonConfig(jsonConfig().
                numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL));
        startUsuario();
    }

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

    @Test
    void pesquisaUsuarioPorId() {
    }

    @Test
    void listarUsuarios() {
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