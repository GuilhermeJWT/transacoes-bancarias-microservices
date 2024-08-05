package br.com.systemsgs.userservice.service.impl;

import br.com.systemsgs.userservice.dto.PayloadTransacaoRequestRabbitMq;
import br.com.systemsgs.userservice.dto.PedidoTransacaoDTO;
import br.com.systemsgs.userservice.exception.erros.BeneficiarioNaoEncontradoException;
import br.com.systemsgs.userservice.exception.erros.UsuarioNaoEncontradoException;
import br.com.systemsgs.userservice.exception.erros.ValorTransacaoMaiorContaAtualException;
import br.com.systemsgs.userservice.model.ModelUsuarios;
import br.com.systemsgs.userservice.repository.UsuariosRepository;
import br.com.systemsgs.userservice.service.PedidoTransacaoService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PedidoTransacaoServiceImpl implements PedidoTransacaoService {

    private final UsuariosRepository usuariosRepository;
    private final AmqpTemplate sendMessageRabbitMq;

    @Autowired
    public PedidoTransacaoServiceImpl(UsuariosRepository usuariosRepository, AmqpTemplate sendMessageRabbitMq) {
        this.usuariosRepository = usuariosRepository;
        this.sendMessageRabbitMq = sendMessageRabbitMq;
    }

    @Transactional
    @Override
    public PedidoTransacaoDTO pedidoTransacao(PedidoTransacaoDTO pedidoTransacaoDTO) {

        var pagador = usuariosRepository.findById(pedidoTransacaoDTO.getIdPagador())
                .orElseThrow(() -> new UsuarioNaoEncontradoException());

        var beneficiario = usuariosRepository.findById(pedidoTransacaoDTO.getIdBeneficiario())
                .orElseThrow(() -> new BeneficiarioNaoEncontradoException());

        validaSaldoAtualPagador(pedidoTransacaoDTO, pagador);
        dadosPedidoTransacao(pagador, beneficiario, pedidoTransacaoDTO);

        return pedidoTransacaoDTO;
    }

    private PayloadTransacaoRequestRabbitMq dadosPedidoTransacao(ModelUsuarios pagador, ModelUsuarios beneficiario, PedidoTransacaoDTO pedidoTransacaoDTO){
        PayloadTransacaoRequestRabbitMq payloadRabbitMq = new PayloadTransacaoRequestRabbitMq();

        payloadRabbitMq.setIdPagador(pagador.getId());
        payloadRabbitMq.setNomePagador(pagador.getNome());
        payloadRabbitMq.setIdBeneficiario(beneficiario.getId());
        payloadRabbitMq.setNomeBeneficiario(beneficiario.getNome());
        payloadRabbitMq.setEmailBeneficiario(beneficiario.getEmail());
        payloadRabbitMq.setValorTransferencia(pedidoTransacaoDTO.getValorTransferencia());
        payloadRabbitMq.setTipoCarteira(pagador.getTipoCarteira().name());

        return payloadRabbitMq;
    }

    private void validaSaldoAtualPagador(PedidoTransacaoDTO pedidoTransacaoDTO, ModelUsuarios pagador){
        if(pedidoTransacaoDTO.getValorTransferencia().compareTo(pagador.getValorConta()) >= 1){//Valor maior que o Saldo Atual da Conta
            throw new ValorTransacaoMaiorContaAtualException();
        }
    }
}
