package br.com.fiap.techhealth.config.kafka.producer;

import br.com.fiap.techhealth.application.dto.response.ConsultationResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ConsultationKafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(ConsultationKafkaProducer.class);
    private static final String TOPIC = "consultation-topic";

    private final KafkaTemplate<String, ConsultationResponseDTO> kafkaTemplate;

    public ConsultationKafkaProducer(KafkaTemplate<String, ConsultationResponseDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(ConsultationResponseDTO consultationDto) {
        logger.info("Enviando mensagem para o Kafka. DTO: {}", consultationDto);
        this.kafkaTemplate.send(TOPIC, consultationDto.id().toString(), consultationDto);
    }
}