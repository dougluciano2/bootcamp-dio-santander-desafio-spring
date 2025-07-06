package br.com.dougluciano.dio.santander.bootcamp.desafiospring.dto;

import br.com.dougluciano.dio.santander.bootcamp.desafiospring.model.Person;

import java.util.UUID;

public record PersonResponseDto(
        UUID id,
        String fullName,
        String phone,
        String email) {

    public PersonResponseDto(Person model){
        this(model.getId(), model.getFullName(), model.getPhone(), model.getEmail());
    }
}
