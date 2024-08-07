package br.com.systemsgs.transaction_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PayloadNotificationTransaction {

    private UUID idTransacao;
    private String nomePagador;
    private String nomeBeneficiario;
    private String emailBeneficiario;
    private BigDecimal valorTransferencia;
    private String dataHoraTransacao;

}
