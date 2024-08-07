package br.com.systemsgs.transactionservice.exception.erros;

public class ErroConverterMessageException extends RuntimeException{

    public ErroConverterMessageException(){
        super("Erro ao tentar converter a mensagem - RabbitMQ");
    }

}
