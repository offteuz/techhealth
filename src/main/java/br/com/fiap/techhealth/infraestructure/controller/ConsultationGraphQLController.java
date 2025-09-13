package br.com.fiap.techhealth.infraestructure.controller;

import br.com.fiap.techhealth.application.dto.request.ConsultationRequestDTO;
import br.com.fiap.techhealth.application.dto.response.ConsultationResponseDTO;
import br.com.fiap.techhealth.domain.service.ConsultationService;
import br.com.fiap.techhealth.exception.AccessDeniedGraphQLException;
import br.com.fiap.techhealth.exception.ConsultationNotFoundException;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ConsultationGraphQLController {

    private final ConsultationService consultationService;

    public ConsultationGraphQLController(ConsultationService consultationService) {
        this.consultationService = consultationService;
    }

    @QueryMapping
    @PreAuthorize("hasRole('PATIENT')")
    public List<ConsultationResponseDTO> consultationByPatient(
            @Argument Long patientId,
            Authentication authentication
    ) {
        return consultationService.findConsultationsForPatientById(authentication, patientId);
    }

    @QueryMapping
    @PreAuthorize("hasRole('PATIENT')")
    public List<ConsultationResponseDTO> getConsultationsForPatient(Authentication authentication) {
        return consultationService.findConsultationsForPatient(authentication);
    }

    @QueryMapping
    @PreAuthorize("hasRole('PATIENT') or hasRole('MEDIC') or hasRole('NURSE')")
    public List<ConsultationResponseDTO> futureConsultations(
            @Argument Long patientId,
            Authentication authentication
    ) {
        return consultationService.findFutureConsultations(authentication, patientId);
    }

    @QueryMapping
    @PreAuthorize("hasRole('PATIENT') or hasRole('MEDIC') or hasRole('NURSE')")
    public ConsultationResponseDTO consultationById(
            Authentication authentication,
            @Argument Long id
    ) {
        return consultationService.findById(authentication, id);
    }

    @MutationMapping
    @PreAuthorize("hasRole('MEDIC') or hasRole('NURSE')")
    public ConsultationResponseDTO createConsultation(
            Authentication authentication,
            @Argument ConsultationRequestDTO dto
    ) {
        return consultationService.create(authentication, dto);
    }

    @MutationMapping
    @PreAuthorize("hasRole('MEDIC') or hasRole('NURSE')")
    public ConsultationResponseDTO updateConsultation(
            Authentication authentication,
            @Argument Long id,
            @Argument ConsultationRequestDTO dto
    ) {
        return consultationService.update(authentication, id, dto);
    }

    @MutationMapping
    @PreAuthorize("hasRole('MEDIC')")
    public Boolean deleteConsultation(
            Authentication authentication,
            @Argument Long id
    ) {
        try {
            consultationService.delete(authentication, id);
            return true;
        } catch (ConsultationNotFoundException e) {
            return false;
        }
    }
}
