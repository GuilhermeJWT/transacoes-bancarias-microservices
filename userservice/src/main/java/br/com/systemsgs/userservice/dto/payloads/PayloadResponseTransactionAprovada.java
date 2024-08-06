package br.com.systemsgs.userservice.dto.payloads;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PayloadResponseTransactionAprovada {

    private Long idPagador;
    private Long idBeneficiario;
    private BigDecimal valorTransferencia;
    private String statusTransacao;

}
