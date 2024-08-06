package br.com.systemsgs.userservice.service.impl;

import br.com.systemsgs.userservice.dto.payloads.PayloadResponseTransactionAprovada;
import br.com.systemsgs.userservice.dto.payloads.PayloadTransacaoRequestRabbitMq;
import br.com.systemsgs.userservice.dto.request.PedidoTransacaoDTO;
import br.com.systemsgs.userservice.enums.StatusTransacao;
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

import static br.com.systemsgs.userservice.config.RabbitMqConfiguration.QUEUE_TRANSACATION;

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
         var pagador = usuariosRepository.findById(pedidoTransacaoDTO.getIdPagador())
              .orElseThrow(() -> new UsuarioNaoEncontradoException());

         var beneficiario = usuariosRepository.findById(pedidoTransacaoDTO.getIdBeneficiario())
                 .orElseThrow(() -> new BeneficiarioNaoEncontradoException());

         validaSaldoAtualPagador(pedidoTransacaoDTO, pagador);
         var dadosTransacao = dadosPedidoTransacao(pagador, beneficiario, pedidoTransacaoDTO);

         rabbitTemplate.convertAndSend(QUEUE_TRANSACATION, dadosTransacao);

        return "A Transação está sendo processada....";
    }

    @Override
    public void dadosPedidoTransacaoAprovada(PayloadResponseTransactionAprovada payloadTransactionAprovada) {
        if(!payloadTransactionAprovada.getStatusTransacao().equals(StatusTransacao.AUTORIZADA)){
            var pagador = usuariosRepository.findById(payloadTransactionAprovada.getIdPagador())
                    .orElseThrow(() -> new UsuarioNaoEncontradoException());

            var beneficiario = usuariosRepository.findById(payloadTransactionAprovada.getIdBeneficiario())
                    .orElseThrow(() -> new BeneficiarioNaoEncontradoException());

            pagador.debitar(payloadTransactionAprovada.getValorTransferencia());
            pagador.multiplicaTotalTransacoesRealizadas(payloadTransactionAprovada.getValorTransferencia());
            pagador.acrescentaTransacoesRealizadas();

            beneficiario.creditar(payloadTransactionAprovada.getValorTransferencia());

            usuariosRepository.save(pagador);
            usuariosRepository.save(beneficiario);
        }
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
