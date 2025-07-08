package br.com.dougluciano.dio.santander.bootcamp.desafiospring.repository;

import br.com.dougluciano.dio.santander.bootcamp.desafiospring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    UserDetails findByUserName(String subject);
}
