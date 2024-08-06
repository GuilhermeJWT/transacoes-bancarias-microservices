package br.com.systemsgs.userservice.controller;

import br.com.systemsgs.userservice.dto.PedidoTransacaoDTO;
import br.com.systemsgs.userservice.service.PedidoTransacaoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transacao")
public class PedidoTransacaoController {

    private final PedidoTransacaoService pedidoTransacaoService;

    public PedidoTransacaoController(PedidoTransacaoService pedidoTransacaoService) {
        this.pedidoTransacaoService = pedidoTransacaoService;
    }

    @PostMapping("/solicitar")
    public String pedidoTransacao(@RequestBody @Valid PedidoTransacaoDTO pedidoTransacaoDTO){
        return pedidoTransacaoService.pedidoTransacao(pedidoTransacaoDTO);
    }
}
