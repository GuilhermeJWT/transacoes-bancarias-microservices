package br.com.systemsgs.transactionservice.dto;

import br.com.systemsgs.transactionservice.enums.StatusPedidoTransacao;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PayloadResponseTransactionAprovada {

    private Long idPagador;
    private Long idBeneficiario;
    private BigDecimal valorTransferencia;
    private StatusPedidoTransacao statusTransacao;

}
