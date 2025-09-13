package br.com.fiap.techhealth.infraestructure.controller;

import br.com.fiap.techhealth.application.dto.request.ConsultationRequestDTO;
import br.com.fiap.techhealth.application.dto.response.ConsultationResponseDTO;
import br.com.fiap.techhealth.domain.service.ConsultationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ConsultationController {

    public final ConsultationService consultationService;

    public ConsultationController(ConsultationService consultationService) {
        this.consultationService = consultationService;
    }

//    @PostMapping("/consultation/create")
//    @ResponseStatus(HttpStatus.CREATED)
//    public ConsultationResponseDTO create(@Valid @RequestBody ConsultationRequestDTO dto) {
//        return consultationService.create(dto);
//    }

//    @GetMapping("/consultation/find-by-id/{idConsultation}")
//    @ResponseStatus(HttpStatus.OK)
//    public ConsultationResponseDTO findById(@PathVariable Long idConsultation) {
//        return consultationService.findById(idConsultation);
//    }

//    @GetMapping("/consultation/find-all")
//    @ResponseStatus(HttpStatus.OK)
//    public List<ConsultationResponseDTO> findAll() {
//        return consultationService.findAll();
//    }

//    @GetMapping("/consultation/find-by-id/me/{idConsultation}")
//    @ResponseStatus(HttpStatus.OK)
//    public ConsultationResponseDTO findConsultationsForPatientById(Authentication authentication, @PathVariable Long idConsultation) throws AccessDeniedException {
//        return consultationService.findConsultationsForPatientById(authentication, idConsultation);
//    }

//    @GetMapping("/consultation/find-all/me")
//    @ResponseStatus(HttpStatus.OK)
//    public List<ConsultationResponseDTO> findConsultationsForPatient(Authentication authentication) {
//        return consultationService.findConsultationsForPatient(authentication);
//    }

//    @DeleteMapping("/consultation/delete/{idConsultation}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void delete(@PathVariable Long idConsultation) {
//        consultationService.delete(idConsultation);
//    }

//    @PatchMapping("/consultation/update/{idConsultation}")
//    @ResponseStatus(HttpStatus.OK)
//    public ConsultationResponseDTO update(@PathVariable Long idConsultation, @Valid @RequestBody ConsultationRequestDTO dto) {
//        return consultationService.update(idConsultation, dto);
//    }
}
