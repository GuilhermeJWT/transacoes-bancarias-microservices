package br.com.systemsgs.userservice.exception;

import br.com.systemsgs.userservice.exception.erros.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestControllerAdviceException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiRestErrors handlerMethodNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){
        List<String> errors =  methodArgumentNotValidException.getBindingResult().getAllErrors()
                .stream().map(erro -> erro.getDefaultMessage())
                .collect(Collectors.toList());

        return new ApiRestErrors(errors);
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiRestErrors usuarioNaoEncontradoException(){
        return new ApiRestErrors(new UsuarioNaoEncontradoException().getMessage());
    }

    @ExceptionHandler(BeneficiarioNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiRestErrors beneficiarioNaoEncontradoException(){
        return new ApiRestErrors(new BeneficiarioNaoEncontradoException().getMessage());
    }

    @ExceptionHandler(ValorTransacaoMaiorContaAtualException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ApiRestErrors valorTransacaoMaiorContaAtualException(){
        return new ApiRestErrors(new ValorTransacaoMaiorContaAtualException().getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ApiRestErrors camposDuplicadoException(){
        return new ApiRestErrors(new CamposDuplicadosException().getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiRestErrors metodoHttpNaoSuportadoException(){
        return new ApiRestErrors(new MetodoHttpNaoSuportadoException().getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiRestErrors httpMessageNotReadableException(){
        return new ApiRestErrors(new PayloadInexistenteException().getMessage());
    }
}
