package br.com.systemsgs.transaction_service.dto;

import br.com.systemsgs.transaction_service.enums.StatusPedidoTransacao;
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
