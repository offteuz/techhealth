package br.com.fiap.techhealth.application.mapper;

import br.com.fiap.techhealth.application.dto.request.ConsultationRequestDTO;
import br.com.fiap.techhealth.application.dto.response.ConsultationResponseDTO;
import br.com.fiap.techhealth.domain.model.Consultation;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring",
        uses = {UserMapperHelper.class},
        unmappedTargetPolicy = ReportingPolicy.WARN)
public interface ConsultationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "idMedic", target = "medic")
    @Mapping(source = "idNurse", target = "nurse")
    @Mapping(source = "idPatient", target = "patient")
    @Mapping(source = "consultationDate", target = "consultationDate") // MapStruct vai usar o método abaixo
    Consultation toModel (ConsultationRequestDTO dto);

    ConsultationResponseDTO toResponseDTO(Consultation consultation);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(ConsultationRequestDTO dto, @MappingTarget Consultation consultation);

    default OffsetDateTime map(LocalDateTime value) {
        return value != null ? value.atOffset(ZoneOffset.UTC) : null;
        // ou outro fuso horário se necessário
    }


}
