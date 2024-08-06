package br.com.systemsgs.userservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PedidoTransacaoDTO {

    @NotNull(message = "Código do Pagador deve ser Informado.")
    @JsonProperty("id_pagador")
    private Long idPagador;

    @NotNull(message = "Código do Beneficiário deve ser Informado.")
    @JsonProperty("id_beneficiario")
    private Long idBeneficiario;

    @NotNull(message = "Informe o valor da Transferência.")
    @Min(value = 1, message = "Valor Minimo da Transferência deve ser de R$ 1.00")
    @Max(value = 10000000, message = "Valor Máximo de uma Transferência não pode ser maior que R$ 100.000,00")
    @JsonProperty("valor_transferencia")
    private BigDecimal valorTransferencia;
}
