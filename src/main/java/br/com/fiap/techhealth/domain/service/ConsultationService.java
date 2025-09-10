package br.com.fiap.techhealth.domain.service;

import br.com.fiap.techhealth.application.dto.request.ConsultationRequestDTO;
import br.com.fiap.techhealth.application.dto.response.ConsultationResponseDTO;
import br.com.fiap.techhealth.application.mapper.ConsultationMapper;
import br.com.fiap.techhealth.domain.model.Consultation;
import br.com.fiap.techhealth.domain.model.ConsultationStatus;
import br.com.fiap.techhealth.domain.model.User;
import br.com.fiap.techhealth.domain.repository.ConsultationRepository;
import br.com.fiap.techhealth.domain.repository.UserRepository;
import br.com.fiap.techhealth.exception.AccessDeniedGraphQLException;
import br.com.fiap.techhealth.exception.ConsultationNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
public class ConsultationService {

    private final ConsultationRepository consultationRepository;

    private final UserRepository userRepository;

    private final ConsultationMapper consultationMapper;

    public ConsultationService(ConsultationRepository consultationRepository, UserRepository userRepository, ConsultationMapper consultationMapper) {
        this.consultationRepository = consultationRepository;
        this.userRepository = userRepository;
        this.consultationMapper = consultationMapper;
    }

    public ConsultationResponseDTO findById(Long idConsultation) {
        Consultation consultation = consultationRepository.findById(idConsultation)
                .orElseThrow(ConsultationNotFoundException::new);

        return new ConsultationResponseDTO(consultation);
    }

    public List<ConsultationResponseDTO> findAll() {
        return consultationRepository.findAll()
                .stream()
                .map(ConsultationResponseDTO::new)
                .toList();
    }

    public ConsultationResponseDTO findConsultationsForPatientById(Authentication authentication, Long idConsultation) throws AccessDeniedGraphQLException {
        String userEmail = authentication.getName();

        Consultation consultation = consultationRepository.findById(idConsultation)
                .orElseThrow(() -> new ConsultationNotFoundException("Consulta não encontrada."));

//        if (!consultation.getPatient().getEmail().trim().equalsIgnoreCase(userEmail.trim())) {
//            throw new AccessDeniedGraphQLException("Você não tem permissão para acessar esta consulta.");
//        }

        return new ConsultationResponseDTO(consultation);
    }

    public List<ConsultationResponseDTO> findConsultationsForPatient(Authentication authentication) {
        String userEmail = authentication.getName();

        User patientUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário autenticado não encontrado no banco de dados."));

        List<Consultation> consultations = consultationRepository.findByPatient(patientUser);

        return consultations.stream()
                .map(consultationMapper::toResponseDTO)
                .toList();
    }

    public ConsultationResponseDTO create(ConsultationRequestDTO dto) {
        Consultation consultation = consultationMapper.toModel(dto);

        consultation.setStatus(ConsultationStatus.SCHEDULED);

        return new ConsultationResponseDTO(consultationRepository.save(consultation));
    }

    public ConsultationResponseDTO update(Long idConsultation, ConsultationRequestDTO dto) {
        Consultation consultation = consultationRepository.findById(idConsultation)
                .orElseThrow(ConsultationNotFoundException::new);

        consultationMapper.updateFromDto(dto, consultation);

        return new ConsultationResponseDTO(consultationRepository.save(consultation));
    }

    public void delete(Long idConsultation) {
        Consultation consultation = consultationRepository.findById(idConsultation)
                .orElseThrow(ConsultationNotFoundException::new);

        consultationRepository.delete(consultation);
    }
}
