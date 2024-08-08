package br.com.systemsgs.userservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Microserviço - User Service",
        description = "Documentação dos endpoints",
        version = "1.0.0"))
public class SwaggerConfiguration {

    public static final String TAG_API_USUARIOS = "Api de Usuarios - V1";
    public static final String TAG_API_TRANSACOES = "Api para Pedidos de Transação - V1";
}
