package br.com.fiap.techhealth.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;

import java.time.LocalDateTime;

public record ConsultationRequestDTO(

        String patientReport,

        @Future(message = "A data da consulta deve ser posterior a data atual.")
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime consultationDate,

        Long idMedic,

        Long idNurse,

        Long idPatient
) {
}
