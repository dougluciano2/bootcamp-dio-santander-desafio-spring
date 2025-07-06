package br.com.dougluciano.dio.santander.bootcamp.desafiospring.repository;

import br.com.dougluciano.dio.santander.bootcamp.desafiospring.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

    boolean existsByEmail(String email);
}
