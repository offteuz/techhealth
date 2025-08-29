package br.com.fiap.techhealth.domain.repository;

import br.com.fiap.techhealth.domain.model.Consultation;
import br.com.fiap.techhealth.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

    List<Consultation> findByPatient(User patient);

    Consultation findByPatientAndId(User patient, Consultation consultation);
}
