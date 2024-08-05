package br.com.systemsgs.transaction_service.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PayloadTransaction {

    private Long idPagador;
    private String nomePagador;
    private Long idBeneficiario;
    private String nomeBeneficiario;
    private String emailBeneficiario;
    private BigDecimal valorTransferencia;
    private String tipoCarteira;

}
