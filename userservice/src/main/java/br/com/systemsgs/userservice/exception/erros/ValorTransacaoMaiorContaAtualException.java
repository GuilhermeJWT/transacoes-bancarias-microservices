package br.com.systemsgs.userservice.exception.erros;

public class ValorTransacaoMaiorContaAtualException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ValorTransacaoMaiorContaAtualException(){
        super("Valor da Transação é maior que o Saldo Atual da Conta!");
    }
}
