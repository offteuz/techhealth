package br.com.fiap.techhealth.domain.service;

import br.com.fiap.techhealth.application.dto.response.ConsultationResponseDTO;
import br.com.fiap.techhealth.domain.model.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private final EmailService emailService;

    public NotificationService(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(
            topics = "consultation-topic",
            groupId = "group_consultation",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void processNotificationQuery(ConsultationResponseDTO consultation, Acknowledgment acknowledgment) {
        logger.info("== MENSAGEM KAFKA RECEBIDA ==");
        logger.info("Evento de consulta recebido para o ID: {}", consultation.id());

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");
            String formattedData = consultation.consultationDate().format(formatter);
            String patientName = consultation.patient().name();
            String medicName = consultation.medic().name();
            String patientEmail = consultation.patient().email();

            String subject = "Confirmação de Agendamento - TechHealth";
            String html = String.format(
                    "<h1>Olá, %s!</h1><p>Sua consulta com o(a) Dr(a). %s foi confirmada para: <strong>%s</strong>.</p>",
                    patientName,
                    medicName,
                    formattedData
            );

            Email emailToSend = new Email(patientEmail, subject, html);
            emailService.send(emailToSend);

            acknowledgment.acknowledge();

        } catch (Exception e) {
            logger.error("Erro ao processar notificação para a consulta {}: {}", consultation.id(), e.getMessage(), e);
        }
    }
}
