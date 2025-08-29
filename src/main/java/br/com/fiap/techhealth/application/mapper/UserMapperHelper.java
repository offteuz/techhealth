package br.com.fiap.techhealth.application.mapper;

import br.com.fiap.techhealth.domain.model.User;
import br.com.fiap.techhealth.domain.repository.UserRepository;
import br.com.fiap.techhealth.exception.UserNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserMapperHelper {

    public final UserRepository userRepository;

    public UserMapperHelper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User map(Long idUser) {
        return userRepository.findById(idUser)
                .orElseThrow(UserNotFoundException::new);
    }
}
