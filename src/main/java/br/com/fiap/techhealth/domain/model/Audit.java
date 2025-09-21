package br.com.fiap.techhealth.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Audit {

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @CreatedDate
    @Column(updatable = false)
    private String createdIn;

    @LastModifiedBy
    private String lastModifiedBy;

    @LastModifiedDate
    private String lastModifiedIn;

    @Override
    public String toString() {
        return "Audit{" +
                "createdBy=" + createdBy +
                ", createdIn=" + createdIn +
                ", lastModifiedBy=" + lastModifiedBy +
                ", lastModifiedIn=" + lastModifiedIn +
                '}';
    }
}
