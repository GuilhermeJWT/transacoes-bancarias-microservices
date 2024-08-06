package br.com.systemsgs.userservice.dto.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PayloadResponseTransactionAprovada {

    private Long idPagador;
    private Long idBeneficiario;
    private BigDecimal valorTransferencia;
    private String statusTransacao;

}
