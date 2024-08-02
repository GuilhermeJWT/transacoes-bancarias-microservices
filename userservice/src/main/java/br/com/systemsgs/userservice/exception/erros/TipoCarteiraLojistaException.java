package br.com.systemsgs.userservice.exception.erros;

public class TipoCarteiraLojistaException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public TipoCarteiraLojistaException(){
        super("Tipo de Carteira: (Lojistas) não podem transferir Dinheiro para Usuários Comuns!");
    }
}
