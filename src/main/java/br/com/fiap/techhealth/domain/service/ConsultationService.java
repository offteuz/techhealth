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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
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

    public List<ConsultationResponseDTO> findAll() {
        return consultationRepository.findAll()
                .stream()
                .map(ConsultationResponseDTO::new)
                .toList();
    }

    public ConsultationResponseDTO findById(Authentication authentication, Long idConsultation) {
        Consultation consultation = consultationRepository.findById(idConsultation)
                .orElseThrow(ConsultationNotFoundException::new);

        validatePatientOwnership(authentication, consultation.getPatient().getId());

        return new ConsultationResponseDTO(consultation);
    }

    public List<ConsultationResponseDTO> findConsultationsForPatientById(Authentication authentication, Long patientId) {
        validatePatientOwnership(authentication, patientId);
        
        return consultationRepository.findAll().stream()
                .filter(c -> c.getPatient().getId().equals(patientId))
                .map(ConsultationResponseDTO::new)
                .toList();
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

    public ConsultationResponseDTO create(Authentication authentication, ConsultationRequestDTO dto) {
        User user = getAuthenticatedUser(authentication);

        if (user.getRole().name().equals("PATIENT")) {
            throw new AccessDeniedGraphQLException("Pacientes não podem criar consultas");
        }

        Consultation consultation = consultationMapper.toModel(dto);
        consultation.setStatus(ConsultationStatus.SCHEDULED);

        return new ConsultationResponseDTO(consultationRepository.save(consultation));
    }

    public ConsultationResponseDTO update(Authentication authentication, Long idConsultation, ConsultationRequestDTO dto) {
        User user = getAuthenticatedUser(authentication);

        if (user.getRole().name().equals("PATIENT")) {
            throw new AccessDeniedGraphQLException("Pacientes não podem atualizar consultas");
        }

        Consultation consultation = consultationRepository.findById(idConsultation)
                .orElseThrow(ConsultationNotFoundException::new);

        consultationMapper.updateFromDto(dto, consultation);

        return new ConsultationResponseDTO(consultationRepository.save(consultation));
    }

    public void delete(Authentication authentication, Long idConsultation) {
        User user = getAuthenticatedUser(authentication);

        if (!user.getRole().name().equals("MEDIC")) {
            throw new AccessDeniedGraphQLException("Somente médicos podem excluir consultas");
        }

        Consultation consultation = consultationRepository.findById(idConsultation)
                .orElseThrow(ConsultationNotFoundException::new);

        consultationRepository.delete(consultation);
    }

    public List<ConsultationResponseDTO> findFutureConsultations(Authentication authentication, Long patientId) {
        User user = getAuthenticatedUser(authentication);

        final Long targetPatientId;

        if (user.getRole().name().equals("PATIENT")) {
            if (patientId != null && !user.getId().equals(patientId)) {
                throw new AccessDeniedGraphQLException("Você não pode consultar consultas futuras de outros pacientes");
            }
            targetPatientId = user.getId();
        } else {
            targetPatientId = patientId;
        }

        ZoneOffset saoPauloOffSet = ZoneOffset.ofHours(-3);
        OffsetDateTime now = OffsetDateTime.now(saoPauloOffSet);

        return consultationRepository.findAll()
                .stream()
                .filter(c -> targetPatientId == null || c.getPatient().getId().equals(targetPatientId))
                .filter(c -> c.getConsultationDate().isAfter(now.toLocalDateTime()))
                .map(ConsultationResponseDTO::new)
                .toList();
    }

    public void validatePatientOwnership(Authentication authentication, Long patientId) {
        User user = getAuthenticatedUser(authentication);

        if (user.getRole().name().equals("PATIENT") && !user.getId().equals(patientId)) {
            throw new AccessDeniedGraphQLException("Você não tem permissão para acessar essa consulta");
        }
    }

    public User getAuthenticatedUser(Authentication authentication) {
        String userEmail = authentication.getName();
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário autenticado não encontrado"));
    }
}
