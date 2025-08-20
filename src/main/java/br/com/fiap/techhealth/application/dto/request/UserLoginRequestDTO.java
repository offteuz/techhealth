package br.com.fiap.techhealth.application.dto.request;

public record UserLoginRequestDTO(

        String email,

        String password
) {
}
