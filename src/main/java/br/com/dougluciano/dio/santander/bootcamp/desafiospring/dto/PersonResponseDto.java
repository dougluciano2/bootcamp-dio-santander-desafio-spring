package br.com.dougluciano.dio.santander.bootcamp.desafiospring.dto;

import br.com.dougluciano.dio.santander.bootcamp.desafiospring.model.Person;

public record PersonResponseDto(
        Long id,
        String fullName,
        String phone,
        String email) {

    public PersonResponseDto(Person model){
        this(model.getId(), model.getFullName(), model.getPhone(), model.getEmail());
    }
}
