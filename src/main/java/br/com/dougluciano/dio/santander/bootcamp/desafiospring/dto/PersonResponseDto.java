package br.com.dougluciano.dio.santander.bootcamp.desafiospring.dto;

import br.com.dougluciano.dio.santander.bootcamp.desafiospring.model.Person;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record PersonResponseDto(
        UUID id,
        String fullName,
        String phone,
        String email,
        List<AddressResponseDto> addresses
) {

    public PersonResponseDto(Person model) {
        this(
                model.getId(),
                model.getFullName(),
                model.getPhone(),
                model.getEmail(),
                model.getAddresses().stream()
                        .map(AddressResponseDto::new)
                        .collect(Collectors.toList())
        );
    }
}
