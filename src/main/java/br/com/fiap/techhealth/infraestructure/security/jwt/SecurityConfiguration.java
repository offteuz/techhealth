package br.com.fiap.techhealth.infraestructure.security.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final UserAuthenticationFilter userAuthenticationFilter;

    public SecurityConfiguration(UserAuthenticationFilter userAuthenticationFilter) {
        this.userAuthenticationFilter = userAuthenticationFilter;
    }

    public static final String [] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
            "/api/auth/login",
            "/api/auth/register",
    };

    // Endpoints que requerem autenticação para serem acessados
    public static final String [] ENDPOINTS_WITH_AUTHENTICATION_REQUIRED = {
            "/api/test"
    };

    // Endpoints que só podem ser acessador por usuários com permissão de "médico(a)"
    public static final String [] ENDPOINTS_DOCTOR = {
            // Test
            "/api/test/doctor",

            // User
            "/api/user/find-all",
            "/api/user/find-by-id/{idUser}",
            "/api/user/delete/{idUser}",
            "/api/user/update/{idUser}",

            // Consultation
            "/api/consultation/find-all",
            "/api/consultation/find-by-id/{idConsultation}",
            "/api/consultation/update/{idConsultation}",
    };

    // Endpoints que só podem ser acessador por usuários com permissão de "enfermeiro(a)"
    public static final String [] ENDPOINTS_NURSE = {
            // Test
            "/api/test/nurse",

            // User
            "/api/user/find-all",
            "/api/user/find-by-id",

            // Consultation
            "/api/consultation/create",
            "/api/consultation/find-all",
            "/api/consultation/find-by-id/{idConsultation}",
            "/api/consultation/delete/{idConsultation}"
    };

    // Endpoints que só podem ser acessador por usuários com permissão de "paciente"
    public static final String [] ENDPOINTS_PATIENT = {
            // Test
            "/api/test/patient",

            // Consultation
            "/api/consultation/find-all/me",
            "/api/consultation/find-by-id/me/{idConsultation}"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/graphql", "/graphiql/**", "/vendor/**").permitAll()
                        .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()
                    .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_REQUIRED).authenticated()
                    .requestMatchers(ENDPOINTS_DOCTOR).hasRole("DOCTOR")
                    .requestMatchers(ENDPOINTS_NURSE).hasRole("NURSE")
                    .requestMatchers(ENDPOINTS_PATIENT).hasRole("PATIENT")
                    .anyRequest().permitAll())
                .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}