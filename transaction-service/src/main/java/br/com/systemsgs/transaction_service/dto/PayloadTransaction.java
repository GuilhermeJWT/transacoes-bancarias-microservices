package br.com.systemsgs.transaction_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PayloadTransaction {

    private Long idPagador;
    private String nomePagador;
    private Long idBeneficiario;
    private String nomeBeneficiario;
    private String emailBeneficiario;
    private BigDecimal valorTransferencia;
    private String tipoCarteira;

}
