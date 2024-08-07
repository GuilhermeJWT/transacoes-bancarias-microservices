package br.com.systemsgs.notificationservice.exception;

import br.com.systemsgs.notificationservice.exception.erros.ErroInternoException;
import br.com.systemsgs.notificationservice.exception.erros.ErroNotificationException;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdviceException {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiRestErrors internalServerErroException(){
        return new ApiRestErrors(new ErroInternoException().getMessage());
    }

    @ExceptionHandler(MessagingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiRestErrors erroNotificationException(){
        return new ApiRestErrors(new ErroNotificationException().getMessage());
    }
}
