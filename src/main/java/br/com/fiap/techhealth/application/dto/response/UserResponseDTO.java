package br.com.fiap.techhealth.application.dto.response;

import br.com.fiap.techhealth.domain.model.Address;
import br.com.fiap.techhealth.domain.model.Audit;
import br.com.fiap.techhealth.domain.model.Role;
import br.com.fiap.techhealth.domain.model.User;

public record UserResponseDTO(

        Long id,

        String name,

        String userName,

        String email,

        //AddressResponseDTO address,

        Address address,

        Role role,

        Audit audit
) {

    public UserResponseDTO(User user) {
        this(
                user.getId(),
                user.getName(),
                user.getUserName(),
                user.getEmail(),
                user.getAddress(),
                //new AddressResponseDTO(user.getAddress()),
                user.getRole(),
                user.getAudit()
        );
    }
}
