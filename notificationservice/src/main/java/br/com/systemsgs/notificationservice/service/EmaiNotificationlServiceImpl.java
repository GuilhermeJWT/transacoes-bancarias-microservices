package br.com.systemsgs.notificationservice.service;

import br.com.systemsgs.notificationservice.dto.PayloadNotificationTransaction;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.context.Context;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmaiNotificationlServiceImpl {

    @Value("${spring.mail.username}")
    private String emailGuilherme;

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    @Autowired
    public EmaiNotificationlServiceImpl(JavaMailSender javaMailSender, SpringTemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    public void sendTransactionEmail(PayloadNotificationTransaction payloadMessage) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        Context context = new Context();
        context.setVariable("idTransacao", payloadMessage.getIdTransacao());
        context.setVariable("nomePagador", payloadMessage.getNomePagador());
        context.setVariable("nomeBeneficiario", payloadMessage.getNomeBeneficiario());
        context.setVariable("valorTransferencia", payloadMessage.getValorTransferencia());
        context.setVariable("dataHoraTransacao", payloadMessage.getDataHoraTransacao());

        String html = templateEngine.process("transaction-email-template", context);

        helper.setFrom(emailGuilherme);
        helper.setTo(payloadMessage.getEmailBeneficiario());
        helper.setText(html, true);
        helper.setSubject("Comprovante de Transação");

        javaMailSender.send(message);
    }
}
