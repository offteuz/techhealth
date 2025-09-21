package br.com.fiap.techhealth.domain.service;

import br.com.fiap.techhealth.exception.GenerateTokenErrorException;
import br.com.fiap.techhealth.exception.ValidateTokenErrorException;
import br.com.fiap.techhealth.infraestructure.security.service.UserDetailsImpl;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class JwtTokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(UserDetailsImpl user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("TechHealth API")
                    .withIssuedAt(creationDate())
                    .withExpiresAt(expirationDate())
                    .withSubject(user.getUsername())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new GenerateTokenErrorException("Ocorreu um erro ao GERAR o token. Verifique!");
        }
    }

    public String getSubjectFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("TechHealth API")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new ValidateTokenErrorException("Token inválido ou expirado.");
        }
    }

    private Instant creationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant();
    }

    private Instant expirationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).plusHours(2).toInstant();
    }

}