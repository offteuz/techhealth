package br.com.fiap.techhealth.infraestructure.controller;

import br.com.fiap.techhealth.domain.model.Consultation;
import br.com.fiap.techhealth.domain.model.User;
import br.com.fiap.techhealth.domain.repository.ConsultationRepository;
import br.com.fiap.techhealth.domain.repository.UserRepository;
import br.com.fiap.techhealth.exception.ConsultationNotFoundException;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ConsultationGraphQLController {

    private final ConsultationRepository consultationRepository;
    private final UserRepository userRepository;

    public ConsultationGraphQLController(ConsultationRepository consultationRepository, UserRepository userRepository) {
        this.consultationRepository = consultationRepository;
        this.userRepository = userRepository;
    }

    @QueryMapping
    public String hello() {
        return "Hello GraphQL!";
    }

    @QueryMapping
    public List<Consultation> consultationsByPatient(@Argument Long patientId) {
        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new UsernameNotFoundException("Paciente não encontrado."));
        return consultationRepository.findByPatient(patient);
    }

    @QueryMapping
    public List<Consultation> futureConsultations(@Argument Long patientId) {
        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new UsernameNotFoundException("Paciente não encontrado."));
        return consultationRepository.findByPatient(patient).stream()
                .filter(c -> c.getConsultationDate().isAfter(LocalDateTime.now()))
                .toList();
    }

    @QueryMapping
    public Consultation consultationById(@Argument Long id) {
        return consultationRepository.findById(id)
                .orElse(null);
    }

    @MutationMapping
    public Consultation createConsultation(
            @Argument Long patientId,
            @Argument Long medicId,
            @Argument Long nurseId,
            @Argument String report,
            @Argument LocalDateTime date
    ) {
        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new UsernameNotFoundException("Paciente não encontrado."));
        User medic = userRepository.findById(medicId)
                .orElseThrow(() -> new UsernameNotFoundException("Médico não encontrado."));
        User nurse = userRepository.findById(nurseId)
                .orElseThrow(() -> new UsernameNotFoundException("Enfermeiro não encontrado."));

        Consultation consultation = new Consultation();
        consultation.setPatient(patient);
        consultation.setMedic(medic);
        consultation.setNurse(nurse);
        consultation.setPatientReport(report);
        consultation.setConsultationDate(date);

        return consultationRepository.save(consultation);
    }

    @MutationMapping
    public Consultation updateConsultation(
            @Argument Long id,
            @Argument String report,
            @Argument LocalDateTime date
    ) {
        Consultation consultation = consultationRepository.findById(id)
                .orElseThrow(() -> new ConsultationNotFoundException("Consulta não encontrada."));

        if (report != null) consultation.setPatientReport(report);
        if (date != null) consultation.setConsultationDate(date);

        return consultationRepository.save(consultation);
    }
}
