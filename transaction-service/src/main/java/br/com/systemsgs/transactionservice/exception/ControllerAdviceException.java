package br.com.systemsgs.transactionservice.exception;

import br.com.systemsgs.transactionservice.exception.erros.ErroConverterMessageException;
import br.com.systemsgs.transactionservice.exception.erros.ErroInternoException;
import br.com.systemsgs.transactionservice.exception.erros.ErroProcessarDadosException;
import br.com.systemsgs.transactionservice.exception.erros.TransacaoNegadaException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdviceException {

    @ExceptionHandler(JsonProcessingException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ApiRestErrors valorTransacaoMaiorContaAtualException(){
        return new ApiRestErrors(new ErroProcessarDadosException().getMessage());
    }

    @ExceptionHandler(TransacaoNegadaException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ApiRestErrors transacaoNegadaException(){
        return new ApiRestErrors(new TransacaoNegadaException().getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiRestErrors internalServerErroException(){
        return new ApiRestErrors(new ErroInternoException().getMessage());
    }

    @ExceptionHandler(MessageConversionException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiRestErrors erroConverterMensagemRabbitMqException(){
        return new ApiRestErrors(new ErroConverterMessageException().getMessage());
    }
}
