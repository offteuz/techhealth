package br.com.fiap.techhealth.application.dto.response;

import br.com.fiap.techhealth.domain.model.Audit;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditResponseDTO {

    private String createdBy;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
            timezone = "America/Sao_Paulo"
    )
    private String createdIn;

    private String LastModifiedBy;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
            timezone = "America/Sao_Paulo"
    )
    private String LastModifiedIn;

    public AuditResponseDTO(Audit audit) {
        this.createdBy = audit.getCreatedBy();
        this.createdIn = audit.getCreatedIn() != null ? audit.getCreatedIn().toString() : null;
        this.LastModifiedBy = audit.getLastModifiedBy();
        this.LastModifiedIn = audit.getLastModifiedIn() != null ? audit.getLastModifiedIn().toString() : null;
    }

}