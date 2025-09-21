package br.com.fiap.techhealth.application.dto.request;

import br.com.fiap.techhealth.domain.model.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(

        @NotBlank(message = "Nome é obrigatório")
        @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
        String name,

        @NotBlank(message = "Nome de usuário é obrigatório")
        @Size(min = 3, max = 50, message = "Nome de usuário deve ter entre 3 e 50 caracteres")
        String userName,

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email deve ter um formato válido")
        String email,

        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 6, max = 100, message = "Senha deve ter entre 6 e 100 caracteres")
        String password,

        @NotNull(message = "Endereço é obrigatório")
        @Valid
        AddressRequestDTO address,

        @NotNull(message = "Papel do usuário é obrigatório")
        Role role
) {
}
