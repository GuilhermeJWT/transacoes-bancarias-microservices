package br.com.systemsgs.userservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PayloadTransacaoRequestRabbitMq {

    @JsonProperty("id_pagador")
    private Long idPagador;

    @JsonProperty("nome_pagador")
    private String nomePagador;

    @JsonProperty("id_beneficiario")
    private Long idBeneficiario;

    @JsonProperty("nome_beneficiario")
    private String nomeBeneficiario;

    @JsonProperty("email_beneficiario")
    private String emailBeneficiario;

    @JsonProperty("valor_transferencia")
    private BigDecimal valorTransferencia;

    @JsonProperty("tipo_carteira")
    private String tipoCarteira;
}
