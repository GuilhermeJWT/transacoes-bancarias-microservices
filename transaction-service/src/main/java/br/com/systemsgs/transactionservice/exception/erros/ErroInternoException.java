package br.com.systemsgs.transactionservice.exception.erros;

public class ErroInternoException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ErroInternoException(){
        super("Erro Interno no Servidor, Contate a equipe de Desenvolvimento.");
    }
}
