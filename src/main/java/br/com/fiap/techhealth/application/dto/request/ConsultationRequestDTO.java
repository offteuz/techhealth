package br.com.fiap.techhealth.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record ConsultationRequestDTO(

        @NotBlank(message = "Relatório do paciente é obrigatório")
        @Size(max = 1000, message = "Relatório deve ter no máximo 1000 caracteres")
        String patientReport,

        @NotNull(message = "Data da consulta é obrigatória")
        @Future(message = "A data da consulta deve ser posterior a data atual")
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime consultationDate,

        @NotNull(message = "ID do médico é obrigatório")
        @Positive(message = "ID do médico deve ser um número positivo")
        Long idMedic,

        @NotNull(message = "ID da enfermeira é obrigatório")
        @Positive(message = "ID da enfermeira deve ser um número positivo")
        Long idNurse,

        @NotNull(message = "ID do paciente é obrigatório")
        @Positive(message = "ID do paciente deve ser um número positivo")
        Long idPatient
) {
}
