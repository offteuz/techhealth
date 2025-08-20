package br.com.fiap.techhealth.application.service;

import br.com.fiap.techhealth.domain.model.User;
import br.com.fiap.techhealth.infraestructure.security.service.UserDetailsImpl;
import br.com.fiap.techhealth.domain.repository.UserRepository;
import br.com.fiap.techhealth.exception.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(UserNotFoundException::new);

        return new UserDetailsImpl(user);
    }
}
