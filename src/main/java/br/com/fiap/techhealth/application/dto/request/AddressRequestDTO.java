package br.com.fiap.techhealth.application.dto.request;

public record AddressRequestDTO(

        String cep,

        String street,

        String number,

        String neighborhood,

        String city,

        String state,

        String country
) {
}
