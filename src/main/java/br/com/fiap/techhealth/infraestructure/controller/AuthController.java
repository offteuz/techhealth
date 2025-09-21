package br.com.fiap.techhealth.infraestructure.controller;

import br.com.fiap.techhealth.application.dto.request.UserLoginRequestDTO;
import br.com.fiap.techhealth.application.dto.request.UserRequestDTO;
import br.com.fiap.techhealth.application.dto.response.TokenResponseDTO;
import br.com.fiap.techhealth.domain.service.AuthService;
import br.com.fiap.techhealth.domain.service.JwtTokenService;
import br.com.fiap.techhealth.infraestructure.security.service.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final AuthService authService;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenService jwtTokenService, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.authService = authService;
    }

    @PostMapping("/auth/login")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponseDTO authenticateUser(@Valid @RequestBody UserLoginRequestDTO dto) {

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());

        Authentication authentication = authenticationManager.authenticate(authToken);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String token = jwtTokenService.generateToken(userDetails);

        return new TokenResponseDTO(token);
    }

    @PostMapping("/auth/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@Valid @RequestBody UserRequestDTO dto) {
        authService.registerUser(dto);
    }

    /*@GetMapping("/test")
    public ResponseEntity<String> getAuthenticationTest() {
        return new ResponseEntity<>("Autenticado com sucesso", HttpStatus.OK);
    }

    @GetMapping("/test/doctor")
    public ResponseEntity<String> getDoctorAuthenticationTest() {
        return new ResponseEntity<>("Médico(a) autenticado com sucesso", HttpStatus.OK);
    }

    @GetMapping("/test/nurse")
    public ResponseEntity<String> getNurseAuthenticationTest() {
        return new ResponseEntity<>("Enfermeiro(a) autenticado com sucesso", HttpStatus.OK);
    }

    @GetMapping("/test/patient")
    public ResponseEntity<String> getPatientAuthenticationTest() {
        return new ResponseEntity<>("Paciente autenticado com sucesso", HttpStatus.OK);
    }*/
}