package br.com.dougluciano.dio.santander.bootcamp.desafiospring.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequestDto(
        @NotBlank String userName,
        @NotBlank String password) {


}
