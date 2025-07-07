package br.com.dougluciano.dio.santander.bootcamp.desafiospring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddressRequestDto(
        @NotBlank @Size(max = 255)
        String street,

        @NotBlank @Size(max = 50)
        String number,

        @Size(max = 255)
        String complement,

        @NotBlank @Size(max = 255)
        String neighborhood,

        @NotBlank @Size(max = 255)
        String city,

        @NotBlank @Size(min = 2, max = 2)
        String state,

        @NotBlank @Size(max = 10)
        String zipCode
) {
}
