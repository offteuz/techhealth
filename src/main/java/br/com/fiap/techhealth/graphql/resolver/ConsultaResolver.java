package br.com.fiap.techhealth.graphql.resolver;

import br.com.fiap.techhealth.domain.model.Consultation;
import br.com.fiap.techhealth.domain.model.User;
import br.com.fiap.techhealth.domain.repository.ConsultationRepository;
import br.com.fiap.techhealth.domain.repository.UserRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ConsultaResolver {

    private final ConsultationRepository consultationRepository;
    private final UserRepository userRepository;

    public ConsultaResolver(ConsultationRepository consultationRepository, UserRepository userRepository) {
        this.consultationRepository = consultationRepository;
        this.userRepository = userRepository;
    }

    // Paciente vê apenas suas consultas
    @PreAuthorize("hasRole('PATIENT')")
    @QueryMapping
    public List<Consultation> consultationsByPatient(@Argument Long patientId) {
        User patient = userRepository.findById(patientId).orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        return consultationRepository.findByPatient(patient);
    }

    // Paciente vê apenas consultas futuras
    @PreAuthorize("hasRole('PATIENT')")
    @QueryMapping
    public List<Consultation> futureConsultations(@Argument Long patientId) {
        User patient = userRepository.findById(patientId).orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        return consultationRepository.findByPatient(patient)
                .stream()
                .filter(c -> c.getConsultationDate().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    // Consulta por ID
    @PreAuthorize("hasAnyRole('DOCTOR','NURSE','PATIENT')")
    @QueryMapping
    public Consultation consultationById(@Argument Long id) {
        return consultationRepository.findById(id).orElseThrow(() -> new RuntimeException("Consulta não encontrada"));
    }
    
    // Criar nova consulta (apenas médicos e enfermeiros)
    @PreAuthorize("hasAnyRole('DOCTOR','NURSE')")
    @MutationMapping
    public Consultation createConsultation(
            @Argument Long patientId,
            @Argument Long medicId,
            @Argument Long nurseId,
            @Argument String report,
            @Argument LocalDateTime date
    ) {
        User patient = userRepository.findById(patientId).orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        User medic = userRepository.findById(medicId).orElseThrow(() -> new RuntimeException("Médico não encontrado"));
        User nurse = userRepository.findById(nurseId).orElseThrow(() -> new RuntimeException("Enfermeiro não encontrado"));

        Consultation consultation = new Consultation();
        consultation.setPatient(patient);
        consultation.setMedic(medic);
        consultation.setNurse(nurse);
        consultation.setPatientReport(report);
        consultation.setConsultationDate(date);

        return consultationRepository.save(consultation);
    }

    // Atualizar consulta existente (apenas médicos e enfermeiros)
    @PreAuthorize("hasAnyRole('DOCTOR','NURSE')")
    @MutationMapping
    public Consultation updateConsultation(
            @Argument Long id,
            @Argument String report,
            @Argument LocalDateTime date
    ) {
        Consultation consultation = consultationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));
        if (report != null) consultation.setPatientReport(report);
        if (date != null) consultation.setConsultationDate(date);

        return consultationRepository.save(consultation);
    }
}
