package br.com.fiap.techhealth.infraestructure.controller;

import br.com.fiap.techhealth.application.dto.request.UserRequestDTO;
import br.com.fiap.techhealth.application.dto.response.UserResponseDTO;
import br.com.fiap.techhealth.domain.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    public final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/find-by-id/{idUser}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDTO findById(@PathVariable Long idUser) {
        return userService.findById(idUser);
    }

    @GetMapping("/user/find-all")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponseDTO> findAll() {
        return userService.findAll();
    }

    @DeleteMapping("/user/delete/{idUser}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long idUser) {
        userService.delete(idUser);
    }

    @PatchMapping("/user/update/{idUser}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDTO update(@PathVariable Long idUser, @Valid @RequestBody UserRequestDTO dto) {
        return userService.update(idUser, dto);
    }
}
