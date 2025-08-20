package br.com.fiap.techhealth.application.dto.response;

import br.com.fiap.techhealth.domain.model.Address;

public record AddressResponseDTO(

        String cep,

        String street,

        String number,

        String neighborhood,

        String city,

        String state,

        String country
) {

    public AddressResponseDTO(Address address) {
        this(
                address.getCep(),
                address.getStreet(),
                address.getNumber(),
                address.getNeighborhood(),
                address.getCity(),
                address.getState(),
                address.getCountry()
        );
    }
}
