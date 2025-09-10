package br.com.fiap.techhealth.infraestructure.controller;

import br.com.fiap.techhealth.application.dto.request.ConsultationRequestDTO;
import br.com.fiap.techhealth.application.dto.response.ConsultationResponseDTO;
import br.com.fiap.techhealth.domain.service.ConsultationService;
import jakarta.annotation.Nullable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Controller
public class ConsultationGraphQLController {

    private final ConsultationService consultationService;

    public ConsultationGraphQLController(ConsultationService consultationService) {
        this.consultationService = consultationService;
    }

    @QueryMapping
    public String hello() {
        return "Hello GraphQL!";
    }

    @QueryMapping
    public ConsultationResponseDTO consultationByPatient(
            @Argument Long patientId,
            Authentication authentication
    ) {
        return consultationService.findConsultationsForPatientById(authentication, patientId);
    }

    @QueryMapping
    public List<ConsultationResponseDTO> myConsultations(Authentication authentication) {
        return consultationService.findConsultationsForPatient(authentication);
    }

    @QueryMapping
    public List<ConsultationResponseDTO> futureConsultations(@Argument Long patientId) {
        ZoneOffset saoPauloOffSet = ZoneOffset.ofHours(-3);
        OffsetDateTime now = OffsetDateTime.now(saoPauloOffSet);
        return consultationService.findAll()
                .stream()
                .filter(c -> c.patient().id().equals(patientId))
                .filter(c -> c.consultationDate().isAfter(now))
                .toList();
    }

    @QueryMapping
    public ConsultationResponseDTO consultationById(@Argument Long id) {
        return consultationService.findById(id);
    }

    @MutationMapping
    public ConsultationResponseDTO createConsultation(@Argument ConsultationRequestDTO dto) {
        return consultationService.create(dto);
    }

    @MutationMapping
    public ConsultationResponseDTO updateConsultation(@Argument Long id, @Argument ConsultationRequestDTO dto) {
        return consultationService.update(id, dto);
    }

    @MutationMapping
    public Boolean deleteConsultation(@Argument Long id) {
        consultationService.delete(id);
        return true;
    }
}
