package br.com.fiap.techhealth.application.dto.response;

import br.com.fiap.techhealth.domain.model.Consultation;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ConsultationResponseDTO(

        Long id,

        String patientReport,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime consultationDate,

        UserResponseDTO medic,

        UserResponseDTO nurse,

        UserResponseDTO patient,

        AuditResponseDTO audit
) {

    public ConsultationResponseDTO(Consultation consultation) {
        this(
                consultation.getId(),
                consultation.getPatientReport(),
                consultation.getConsultationDate(),
                new UserResponseDTO(consultation.getMedic()),
                new UserResponseDTO(consultation.getNurse()),
                new UserResponseDTO(consultation.getPatient()),
                new AuditResponseDTO(consultation.getAudit())
        );
    }
}
