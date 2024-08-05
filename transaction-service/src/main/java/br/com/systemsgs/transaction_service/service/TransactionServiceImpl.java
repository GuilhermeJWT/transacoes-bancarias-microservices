package br.com.systemsgs.transaction_service.service;

import br.com.systemsgs.transaction_service.dto.PayloadTransaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService{

    @Override
    public void validaTransacao(PayloadTransaction payloadTransaction) {

    }
}
