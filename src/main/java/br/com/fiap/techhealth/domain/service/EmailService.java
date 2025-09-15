package br.com.fiap.techhealth.domain.service;

import br.com.fiap.techhealth.domain.model.Email;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void send(Email email) {
        logger.info("Iniciando envio de e-mail para: {}", email.to());

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("techhealth@fiap.com.br");
            helper.setTo(email.to());
            helper.setSubject(email.subject());
            helper.setText(email.body(), true);

            mailSender.send(message);
            logger.info("E-mail enviado com sucesso para: {}", email.to());

        } catch (MessagingException e) {
            logger.error("Falha ao enviar e-mail para: {}", email.to(), e);
            throw new RuntimeException(e);
        }
    }
}
