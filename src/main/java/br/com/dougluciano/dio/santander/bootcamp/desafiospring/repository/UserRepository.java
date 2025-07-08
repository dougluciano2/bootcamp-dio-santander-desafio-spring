package br.com.dougluciano.dio.santander.bootcamp.desafiospring.repository;

import br.com.dougluciano.dio.santander.bootcamp.desafiospring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUserName(String subject);
}
