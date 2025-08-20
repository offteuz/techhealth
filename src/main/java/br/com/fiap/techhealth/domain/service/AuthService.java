package br.com.fiap.techhealth.domain.service;

import br.com.fiap.techhealth.application.dto.request.UserRequestDTO;
import br.com.fiap.techhealth.application.mapper.AddressMapper;
import br.com.fiap.techhealth.domain.model.Address;
import br.com.fiap.techhealth.domain.model.User;
import br.com.fiap.techhealth.domain.repository.UserRepository;
import br.com.fiap.techhealth.infraestructure.security.service.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AddressMapper addressMapper;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AddressMapper addressMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.addressMapper = addressMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));
        return new UserDetailsImpl(user);
    }

    public User registerUser(UserRequestDTO dto) {
        if (this.userRepository.existsUserByEmail(dto.email())) {
            throw new RuntimeException("Email já cadastrado.");
        }

        String encryptedPassword = passwordEncoder.encode(dto.password());

        Address addressModel = addressMapper.toModel(dto.address());

        User newUser = new User(dto.name(), dto.userName(), dto.email(), encryptedPassword, addressModel, dto.role());

        return this.userRepository.save(newUser);
    }
}