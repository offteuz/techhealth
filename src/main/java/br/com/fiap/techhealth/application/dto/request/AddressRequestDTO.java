package br.com.fiap.techhealth.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AddressRequestDTO(

        @NotBlank(message = "CEP é obrigatório")
        @Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 dígitos")
        String cep,

        @NotBlank(message = "Rua é obrigatória")
        @Size(max = 100, message = "Rua deve ter no máximo 100 caracteres")
        String street,

        @NotBlank(message = "Número é obrigatório")
        @Size(max = 10, message = "Número deve ter no máximo 10 caracteres")
        String number,

        @NotBlank(message = "Bairro é obrigatório")
        @Size(max = 50, message = "Bairro deve ter no máximo 50 caracteres")
        String neighborhood,

        @NotBlank(message = "Cidade é obrigatória")
        @Size(max = 50, message = "Cidade deve ter no máximo 50 caracteres")
        String city,

        @NotBlank(message = "Estado é obrigatório")
        @Size(max = 50, message = "Estado deve ter no máximo 50 caracteres")
        String state,

        @NotBlank(message = "País é obrigatório")
        @Size(max = 50, message = "País deve ter no máximo 50 caracteres")
        String country
) {
}
