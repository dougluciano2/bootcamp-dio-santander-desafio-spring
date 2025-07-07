package br.com.dougluciano.dio.santander.bootcamp.desafiospring.repository;

import br.com.dougluciano.dio.santander.bootcamp.desafiospring.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
}
