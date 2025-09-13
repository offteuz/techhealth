package br.com.fiap.techhealth.application.dto.response;

import br.com.fiap.techhealth.application.mapper.AuditMapper;
import br.com.fiap.techhealth.domain.model.Audit;
import br.com.fiap.techhealth.domain.model.Consultation;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public record ConsultationResponseDTO(

        Long id,

        String patientReport,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        OffsetDateTime consultationDate,

        UserResponseDTO medic,

        UserResponseDTO nurse,

        UserResponseDTO patient,

        AuditResponseDTO audit
) {

        public ConsultationResponseDTO(Consultation consultation) {
                this(
                        consultation.getId(),
                        consultation.getPatientReport(),
                        consultation.getConsultationDate() != null
                                ? consultation.getConsultationDate().atOffset(ZoneOffset.ofHours(-3)) // São Paulo
                                : null,
                        new UserResponseDTO(consultation.getMedic()),
                        new UserResponseDTO(consultation.getNurse()),
                        new UserResponseDTO(consultation.getPatient()),
                        //AuditMapper.toDTO(consultation.getAudit())
                        consultation.getAudit()
                );
        }
}
