package br.com.systemsgs.userservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponse{

   @JsonProperty("codigo_usuario")
   private Long id;

   @JsonProperty("nome_usuario")
   private String nome;

   @JsonProperty("email")
   private String email;

   @JsonProperty("cpf")
   private String cpf;

   @JsonProperty("valor_na_conta")
   private BigDecimal valorConta;

   @JsonProperty("tipo_carteira")
   private String tipoCarteira;

   @JsonProperty("quantidade_transacoes_realizadas")
   private Integer quantidadeTransacoesRealizadas;

   @JsonProperty("total_transacoes_realizadas")
   private BigDecimal totalTransacoesRealizadas;

   @JsonProperty("data_criacao_do_usuario")
   private LocalDateTime dataCriacao;

   @JsonProperty("data_ultima_alteracao_do_usuario")
   private LocalDateTime dataAlteracao;
}
