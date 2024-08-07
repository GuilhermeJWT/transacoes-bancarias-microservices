package br.com.systemsgs.notificationservice.exception.erros;

public class ErroNotificationException extends RuntimeException{

    public ErroNotificationException(){
        super("Erro ao tentar enviar a Notificação por E-mail. ");
    }
}
