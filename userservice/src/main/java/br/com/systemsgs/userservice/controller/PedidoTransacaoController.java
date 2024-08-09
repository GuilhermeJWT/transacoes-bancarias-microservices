package br.com.systemsgs.userservice.controller;

import br.com.systemsgs.userservice.dto.request.PedidoTransacaoDTO;
import br.com.systemsgs.userservice.service.PedidoTransacaoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static br.com.systemsgs.userservice.config.SwaggerConfiguration.TAG_API_TRANSACOES;

@Tag(name = TAG_API_TRANSACOES)
@RestController
@RequestMapping("api/v1/transacao")
public class PedidoTransacaoController {

    private final PedidoTransacaoService pedidoTransacaoService;

    @Autowired
    public PedidoTransacaoController(PedidoTransacaoService pedidoTransacaoService) {
        this.pedidoTransacaoService = pedidoTransacaoService;
    }

    @PostMapping("/solicitar")
    public String pedidoTransacao(@RequestBody @Valid PedidoTransacaoDTO pedidoTransacaoDTO){
        return pedidoTransacaoService.pedidoTransacao(pedidoTransacaoDTO);
    }
}
