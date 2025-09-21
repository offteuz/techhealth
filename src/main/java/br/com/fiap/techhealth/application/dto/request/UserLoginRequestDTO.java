package br.com.fiap.techhealth.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLoginRequestDTO(

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email deve ter um formato válido")
        String email,

        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
        String password
) {
}
