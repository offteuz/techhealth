package br.com.fiap.techhealth.infraestructure.controller;

import br.com.fiap.techhealth.application.dto.request.ConsultationRequestDTO;
import br.com.fiap.techhealth.application.dto.response.ConsultationResponseDTO;
import br.com.fiap.techhealth.domain.service.ConsultationService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Controller
public class ConsultationGraphQLController {

    private final ConsultationService consultationService;

    public ConsultationGraphQLController(ConsultationService consultationService) {
        this.consultationService = consultationService;
    }

    @QueryMapping
    @PreAuthorize("hasRole('PATIENT')")
    public ConsultationResponseDTO consultationByPatient(
            @Argument Long idConsultation,
            Authentication authentication
    ) throws AccessDeniedException {
        return consultationService.findConsultationsForPatientById(authentication, idConsultation);
    }

    @QueryMapping
    @PreAuthorize("hasRole('PATIENT')")
    public List<ConsultationResponseDTO> getConsultationsForPatient(Authentication authentication) {
        return consultationService.findConsultationsForPatient(authentication);
    }

    @QueryMapping
    @PreAuthorize("hasRole('PATIENT') or hasRole('DOCTOR') or hasRole('NURSE')")
    public List<ConsultationResponseDTO> futureConsultations(
            @Argument Long patientId,
            Authentication authentication
    ) {
        return consultationService.findFutureConsultations(authentication, patientId);
    }

    @QueryMapping
    @PreAuthorize("hasRole('DOCTOR') or hasRole('NURSE')")
    public ConsultationResponseDTO consultationById(
            Authentication authentication,
            @Argument Long id
    ) {
        return consultationService.findById(id);
    }

    @MutationMapping
    @PreAuthorize("hasRole('DOCTOR') or hasRole('NURSE')")
    public ConsultationResponseDTO createConsultation(
            Authentication authentication,
            @Argument ConsultationRequestDTO dto
    ) {
        return consultationService.create(dto);
    }

    @MutationMapping
    @PreAuthorize("hasRole('DOCTOR') or hasRole('NURSE')")
    public ConsultationResponseDTO updateConsultation(
            Authentication authentication,
            @Argument Long id,
            @Argument ConsultationRequestDTO dto
    ) {
        return consultationService.update(id, dto);
    }


    @QueryMapping
    @PreAuthorize("hasRole('DOCTOR') or hasRole('NURSE')")
    public List<ConsultationResponseDTO> findAllConsultations(Authentication authentication) {
        return consultationService.findAll();
    }

    @QueryMapping
    @PreAuthorize("hasRole('PATIENT')")
    public List<ConsultationResponseDTO> findAllMyConsultations(Authentication authentication) {
        return consultationService.findConsultationsForPatient(authentication);
    }

    @QueryMapping
    @PreAuthorize("hasRole('PATIENT')")
    public ConsultationResponseDTO findMyConsultationById(
            @Argument Long id,
            Authentication authentication
    ) throws AccessDeniedException {
        return consultationService.findConsultationsForPatientById(authentication, id);
    }

    @QueryMapping
    @PreAuthorize("hasRole('PATIENT')")
    public List<ConsultationResponseDTO> findMyFutureConsultations(Authentication authentication) {
        return consultationService.findMyFutureConsultations(authentication);
    }

    @QueryMapping
    @PreAuthorize("hasRole('DOCTOR')")
    public List<ConsultationResponseDTO> findPastConsultationsByDoctor(
            @Argument Long patientId,
            Authentication authentication
    ) {
        return consultationService.findPastConsultationsByDoctor(authentication, patientId);
    }

    @QueryMapping
    @PreAuthorize("hasRole('DOCTOR')")
    public List<ConsultationResponseDTO> findFutureConsultationsByDoctor(
            @Argument Long patientId,
            Authentication authentication
    ) {
        return consultationService.findFutureConsultationsByDoctor(authentication, patientId);
    }
}