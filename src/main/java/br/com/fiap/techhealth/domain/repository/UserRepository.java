package br.com.fiap.techhealth.domain.repository;

import br.com.fiap.techhealth.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsUserByEmail(String email);

    boolean existsUserByUserName(String userName);
}
