package br.com.systemsgs.userservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record UsuarioResponse

        (
         @JsonProperty("codigo_usuario")
         Long id,

         @JsonProperty("nome_usuario")
         String nome,

         @JsonProperty("email")
         String email,

         @JsonProperty("cpf")
         String cpf,

         @JsonProperty("valor_na_conta")
         BigDecimal valorConta,

         @JsonProperty("tipo_carteira")
         String tipoCarteira,

         @JsonProperty("quantidade_transacoes_realizadas")
         Integer quantidadeTransacoesRealizadas,

         @JsonProperty("total_transacoes_realizadas")
         BigDecimal totalTransacoesRealizadas,

         @JsonProperty("data_criacao_do_usuario")
         LocalDateTime dataCriacao,

         @JsonProperty("data_ultima_alteracao_do_usuario")
         LocalDateTime dataAlteracao
        ){}
