package br.com.fiap.techhealth.infraestructure.graphql;

import br.com.fiap.techhealth.domain.model.Consultation;
import br.com.fiap.techhealth.domain.model.User;
import br.com.fiap.techhealth.domain.repository.ConsultationRepository;
import br.com.fiap.techhealth.domain.repository.UserRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ConsultationGraphQL {

    private final ConsultationRepository consultationRepository;
    private final UserRepository userRepository;

    public ConsultationGraphQL(ConsultationRepository consultationRepository, UserRepository userRepository) {
        this.consultationRepository = consultationRepository;
        this.userRepository = userRepository;
    }

    @QueryMapping
    public List<Consultation> consultationsByPatient(@Argument Long patientId) {
        User patient = userRepository.findById(patientId).orElseThrow();
        return consultationRepository.findByPatient(patient);
    }

    @QueryMapping
    public List<Consultation> futureConsultations(@Argument Long patientId) {
        User patient = userRepository.findById(patientId).orElseThrow();
        return consultationRepository.findByPatient(patient).stream()
                .filter(c -> c.getConsultationDate().isAfter(LocalDateTime.now()))
                .toList();
    }

    @QueryMapping
    public Consultation consultationById(@Argument Long id) {
        return consultationRepository.findById(id).orElse(null);
    }

    @MutationMapping
    public Consultation createConsultation(
            @Argument Long patientId,
            @Argument Long medicId,
            @Argument Long nurseId,
            @Argument String report,
            @Argument LocalDateTime date
    ) {
        User patient = userRepository.findById(patientId).orElseThrow();
        User medic = userRepository.findById(medicId).orElseThrow();
        User nurse = userRepository.findById(nurseId).orElseThrow();

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
        Consultation consultation = consultationRepository.findById(id).orElseThrow();
        if (report != null) consultation.setPatientReport(report);
        if (date != null) consultation.setConsultationDate(date);
        return consultationRepository.save(consultation);
    }
}
