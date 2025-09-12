package br.com.fiap.techhealth.application.mapper;

import br.com.fiap.techhealth.application.dto.response.AuditResponseDTO;
import br.com.fiap.techhealth.domain.model.Audit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class AuditMapper {

    public static AuditResponseDTO toDTO(Audit audit) {
        if (audit == null) {
            return null;
        }
        return new AuditResponseDTO(
                audit.getCreateBy(),
                audit.getCreatedIn(),
                audit.getLastModifiedBy(),
                audit.getLastModifiedIn()
        );
    }

}
