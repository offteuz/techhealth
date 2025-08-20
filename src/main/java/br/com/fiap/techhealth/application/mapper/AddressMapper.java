package br.com.fiap.techhealth.application.mapper;

import br.com.fiap.techhealth.application.dto.request.AddressRequestDTO;
import br.com.fiap.techhealth.domain.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN)
public interface AddressMapper {

    Address toModel(AddressRequestDTO dto);
}
