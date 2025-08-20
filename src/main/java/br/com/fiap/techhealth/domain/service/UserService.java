package br.com.fiap.techhealth.domain.service;

import br.com.fiap.techhealth.application.dto.request.UserRequestDTO;
import br.com.fiap.techhealth.application.dto.response.UserResponseDTO;
import br.com.fiap.techhealth.application.mapper.UserMapper;
import br.com.fiap.techhealth.domain.model.User;
import br.com.fiap.techhealth.domain.repository.UserRepository;
import br.com.fiap.techhealth.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponseDTO findById(Long idUser) {
        User user = userRepository.findById(idUser)
                .orElseThrow(UserNotFoundException::new);

        return new UserResponseDTO(user);
    }

    public List<UserResponseDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserResponseDTO::new)
                .toList();
    }

    public void delete(Long idUser) {
        User user = userRepository.findById(idUser)
                .orElseThrow(UserNotFoundException::new);

        userRepository.delete(user);
    }

    public UserResponseDTO update(Long idUser, UserRequestDTO dto) {
        User user = userRepository.findById(idUser)
                .orElseThrow(UserNotFoundException::new);

        userMapper.updateFromDto(dto, user);

        User userSaved = userRepository.save(user);

        return new UserResponseDTO(userSaved);
    }
}
