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
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.com.systemsgs.userservice.config.ApplicationConfiguration.QUEUE_TRANSACATION;

@Slf4j
@Service
public class PedidoTransacaoServiceImpl implements PedidoTransacaoService {

    private final UsuariosRepository usuariosRepository;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public PedidoTransacaoServiceImpl(UsuariosRepository usuariosRepository, RabbitTemplate rabbitTemplate) {
        this.usuariosRepository = usuariosRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Transactional
    @Override
    public String pedidoTransacao(PedidoTransacaoDTO pedidoTransacaoDTO) {
        try {
         var pagador = usuariosRepository.findById(pedidoTransacaoDTO.getIdPagador())
              .orElseThrow(() -> new UsuarioNaoEncontradoException());

         var beneficiario = usuariosRepository.findById(pedidoTransacaoDTO.getIdBeneficiario())
                 .orElseThrow(() -> new BeneficiarioNaoEncontradoException());

         validaSaldoAtualPagador(pedidoTransacaoDTO, pagador);
         var dadosTransacao = dadosPedidoTransacao(pagador, beneficiario, pedidoTransacaoDTO);

         rabbitTemplate.convertAndSend(QUEUE_TRANSACATION, dadosTransacao);

        }catch (Exception e){
            log.error("Erro ao tentar realizar a Transação {}", e.getMessage());
            return "Erro ao tentar realizar a Transação, tente novamente mais tarde.";
        }
        return "A Transação está sendo processada.";
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
