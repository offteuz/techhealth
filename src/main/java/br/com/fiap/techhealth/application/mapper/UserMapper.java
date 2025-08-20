package br.com.fiap.techhealth.application.mapper;

import br.com.fiap.techhealth.application.dto.request.UserRequestDTO;
import br.com.fiap.techhealth.domain.model.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN)
public interface UserMapper {

    UserRequestDTO toModel(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(UserRequestDTO dto, @MappingTarget User user);
}
