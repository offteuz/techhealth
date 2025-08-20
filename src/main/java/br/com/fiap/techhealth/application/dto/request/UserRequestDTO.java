package br.com.fiap.techhealth.application.dto.request;

import br.com.fiap.techhealth.domain.model.Role;


public record UserRequestDTO(

        String name,

        String userName,

        String email,

        String password,

        AddressRequestDTO address,

        Role role
) {
}
