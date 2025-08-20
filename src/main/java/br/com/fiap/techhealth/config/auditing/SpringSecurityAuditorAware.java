package br.com.fiap.techhealth.config.auditing;

import br.com.fiap.techhealth.infraestructure.security.service.UserDetailsImpl;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            return Optional.of("SYSTEM");
        }

        Object principal = authentication.getPrincipal();

        // Verifica se o principal é a sua classe UserDetailsImpl
        if (principal instanceof UserDetailsImpl) {
            // Faz o cast, pega a entidade User interna e, finalmente, o userName.
            UserDetailsImpl userDetails = (UserDetailsImpl) principal;
            return Optional.ofNullable(userDetails.getUser().getUserName());
        }

        // Fallback, caso o principal seja outra coisa.
        return Optional.of(principal.toString());
    }
}