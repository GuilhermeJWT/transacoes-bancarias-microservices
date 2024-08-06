package br.com.systemsgs.transaction_service.exception.erros;

public class TransacaoNegadaException extends RuntimeException{

    public TransacaoNegadaException(){
        super("Transação Negada! apenas Usuários Comuns podem Transferir Dinheiro.");
    }

}
