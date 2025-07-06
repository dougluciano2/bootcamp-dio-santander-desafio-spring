package br.com.dougluciano.dio.santander.bootcamp.desafiospring.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PersonRequestDto(

    @NotBlank(message = "Full name cannot be blank")
    String fullName,

    @NotBlank(message = "Phone cannot be blank")
    String phone,

    @Email(message = "Provide a valid email address")
    @NotBlank(message = "Email cannot be blank")
    String email
) {

}
