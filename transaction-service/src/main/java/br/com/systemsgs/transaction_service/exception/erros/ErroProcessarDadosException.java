package br.com.systemsgs.transaction_service.exception.erros;

public class ErroProcessarDadosException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ErroProcessarDadosException(){
        super("Erro ao processar os dados da Transação.");
    }
}
