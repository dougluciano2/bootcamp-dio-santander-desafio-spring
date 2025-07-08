package br.com.dougluciano.dio.santander.bootcamp.desafiospring.repository;

import br.com.dougluciano.dio.santander.bootcamp.desafiospring.model.LoginAndCreateUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LoginAndCreateUserRepository extends JpaRepository<LoginAndCreateUser, UUID> {
}
