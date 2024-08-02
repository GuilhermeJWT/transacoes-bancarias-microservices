package br.com.systemsgs.userservice.exception;

public class BeneficiarioNaoEncontradoException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public BeneficiarioNaoEncontradoException() {
        super("Beneficiario não Encontrado!");
    }
}
