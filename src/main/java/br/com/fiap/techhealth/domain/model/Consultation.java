package br.com.fiap.techhealth.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_CONSULTATION")
@EntityListeners(AuditingEntityListener.class)
public class Consultation {

    @Column(name = "id_consultation")
    @Id
    @SequenceGenerator(name = "seq_consultation", sequenceName = "seq_consultation", allocationSize = 1)
    @GeneratedValue(generator = "seq_consultation", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "patient_report", nullable = false)
    private String patientReport;

    @Column(name = "consultation_date", nullable = false)
    private LocalDateTime consultationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(
            name = "id_medic",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_consultation_medic"))
    private User medic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(
            name = "id_nurse",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_consultation_nurse"))
    private User nurse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(
            name = "id_patient",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_consultation_patient"))
    private User patient;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ConsultationStatus status;

    @Embedded
    private Audit audit = new Audit();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Consultation that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Consultation{" +
                "id=" + id +
                ", patientReport='" + patientReport + '\'' +
                ", consultationDate=" + consultationDate +
                ", medic=" + medic +
                ", nurse=" + nurse +
                ", patient=" + patient +
                ", audit=" + audit +
                '}';
    }
}
