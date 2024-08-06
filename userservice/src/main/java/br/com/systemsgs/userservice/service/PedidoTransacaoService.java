package br.com.systemsgs.userservice.service;

import br.com.systemsgs.userservice.dto.payloads.PayloadResponseTransactionAprovada;
import br.com.systemsgs.userservice.dto.request.PedidoTransacaoDTO;

public interface PedidoTransacaoService {

    String pedidoTransacao(PedidoTransacaoDTO pedidoTransacaoDTO);

    void dadosPedidoTransacaoAprovada(PayloadResponseTransactionAprovada payloadTransactionAprovada);

}
