package br.com.systemsgs.userservice.exception.erros;

public class CamposDuplicadosException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public CamposDuplicadosException() {
        super("Campos já cadastrados na base de dados, Por Favor, Informe outros!");
    }
}
