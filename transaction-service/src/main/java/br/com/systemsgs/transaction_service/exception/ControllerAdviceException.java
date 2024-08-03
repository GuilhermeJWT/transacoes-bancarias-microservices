package br.com.systemsgs.transaction_service.exception;

import br.com.systemsgs.transaction_service.exception.erros.ErroInternoException;
import br.com.systemsgs.transaction_service.exception.erros.ErroProcessarDadosException;
import com.fasterxml.jackson.core.JsonProcessingException;
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

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiRestErrors internalServerErroException(){
        return new ApiRestErrors(new ErroInternoException().getMessage());
    }
}
