package br.com.systemsgs.userservice.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class PedidoTransacaoDTO {

    private Long idUsuario;
    private BigDecimal valorConta;
    private String tipoPessoa;

}
