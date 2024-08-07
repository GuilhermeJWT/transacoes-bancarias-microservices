package br.com.systemsgs.notificationservice.listener;

import br.com.systemsgs.notificationservice.dto.PayloadNotificationTransaction;
import br.com.systemsgs.notificationservice.exception.erros.ErroNotificationException;
import br.com.systemsgs.notificationservice.service.EmaiNotificationlServiceImpl;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.messaging.Message;

import static br.com.systemsgs.notificationservice.config.RabbitMqConfiguration.QUEUE_NOTIFICATION_BENEFICIARIO;

@Slf4j
@Component
public class NotificationListener {

    private final EmaiNotificationlServiceImpl emailNotificationService;

    @Autowired
    public NotificationListener(EmaiNotificationlServiceImpl emailNotificationService) {
        this.emailNotificationService = emailNotificationService;
    }

    @RabbitListener(queues = QUEUE_NOTIFICATION_BENEFICIARIO)
    public void dadosNotificationBeneficiario(Message<PayloadNotificationTransaction> payloadMessage){
        try {
            emailNotificationService.sendTransactionEmail(payloadMessage.getPayload());
        } catch (MessagingException e) {
            log.error("Erro ao tentar receber Payload de notificação do Beneficiário.");
            throw new ErroNotificationException();
        }
    }
}
