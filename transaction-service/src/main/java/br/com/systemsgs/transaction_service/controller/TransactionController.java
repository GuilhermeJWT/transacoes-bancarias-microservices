package br.com.systemsgs.transaction_service.controller;

import br.com.systemsgs.transaction_service.model.ModelTransaction;
import br.com.systemsgs.transaction_service.repository.TransactionRepository;
import br.com.systemsgs.transaction_service.service.TransactionServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {

    private final TransactionServiceImpl transactionService;
    private final TransactionRepository transactionRepository;

    public TransactionController(TransactionServiceImpl transactionService, TransactionRepository transactionRepository) {
        this.transactionService = transactionService;
        this.transactionRepository = transactionRepository;
    }

    @GetMapping(value = "/listar")
    public List<ModelTransaction> returnaDadosTransacao(){
        return transactionRepository.findAll();
    }

}
