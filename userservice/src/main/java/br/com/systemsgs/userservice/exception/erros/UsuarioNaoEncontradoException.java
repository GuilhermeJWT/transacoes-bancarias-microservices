package br.com.systemsgs.userservice.exception.erros;

public class UsuarioNaoEncontradoException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public UsuarioNaoEncontradoException() {
        super("Usuário não Encontrado!");
    }
}
