package br.com.systemsgs.userservice.service.impl;

import br.com.systemsgs.userservice.dto.PayloadTransacaoRequestRabbitMq;
import br.com.systemsgs.userservice.dto.PedidoTransacaoDTO;
import br.com.systemsgs.userservice.enums.TipoCarteira;
import br.com.systemsgs.userservice.exception.erros.BeneficiarioNaoEncontradoException;
import br.com.systemsgs.userservice.exception.erros.TipoCarteiraLojistaException;
import br.com.systemsgs.userservice.exception.erros.UsuarioNaoEncontradoException;
import br.com.systemsgs.userservice.exception.erros.ValorTransacaoMaiorContaAtualException;
import br.com.systemsgs.userservice.model.ModelUsuarios;
import br.com.systemsgs.userservice.repository.UsuariosRepository;
import br.com.systemsgs.userservice.service.PedidoTransacaoService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PedidoTransacaoServiceImpl implements PedidoTransacaoService {

    private final UsuariosRepository usuariosRepository;

    public PedidoTransacaoServiceImpl(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    @Transactional
    @Override
    public PedidoTransacaoDTO pedidoTransacao(PedidoTransacaoDTO pedidoTransacaoDTO) {
        PayloadTransacaoRequestRabbitMq payloadRabbitMq = new PayloadTransacaoRequestRabbitMq();

        var pagador = usuariosRepository.findById(pedidoTransacaoDTO.getIdPagador())
                .orElseThrow(() -> new UsuarioNaoEncontradoException());

        var beneficiario = usuariosRepository.findById(pedidoTransacaoDTO.getIdBeneficiario())
                .orElseThrow(() -> new BeneficiarioNaoEncontradoException());

        validaTipoCarteiraSaldoAtual(pedidoTransacaoDTO, pagador);

        payloadRabbitMq.setIdPagador(pagador.getId());
        payloadRabbitMq.setNomePagador(pagador.getNome());
        payloadRabbitMq.setIdBeneficiario(beneficiario.getId());
        payloadRabbitMq.setNomeBeneficiario(beneficiario.getNome());
        payloadRabbitMq.setEmailBeneficiario(beneficiario.getEmail());
        payloadRabbitMq.setValorTransferencia(pedidoTransacaoDTO.getValorTransferencia());
        payloadRabbitMq.setTipoCarteira(pagador.getTipoCarteira().name());

        return pedidoTransacaoDTO;
    }

    private void validaTipoCarteiraSaldoAtual(PedidoTransacaoDTO pedidoTransacaoDTO, ModelUsuarios pagador){
        /*if(pagador.getTipoCarteira().equals(TipoCarteira.LOJISTA)){
            throw new TipoCarteiraLojistaException();
        }*/
        //Valor maior que o Saldo Atual da Conta
        if(pedidoTransacaoDTO.getValorTransferencia().compareTo(pagador.getValorConta()) >= 1){
            throw new ValorTransacaoMaiorContaAtualException();
        }
    }
}
