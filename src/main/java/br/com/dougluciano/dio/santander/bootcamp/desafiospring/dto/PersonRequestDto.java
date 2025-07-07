package br.com.dougluciano.dio.santander.bootcamp.desafiospring.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record PersonRequestDto(

        @NotBlank(message = "Full name cannot be blank")
        @NotNull(message = "Filed cannot be null")
        @NotNull
        @Size(min = 1, max = 200, message = "Name value must be between 1 and 150 characters")
        String fullName,

        @NotBlank(message = "Phone cannot be blank")
        @NotNull(message = "Filed cannot be null")
        @Size(min = 7, max = 14, message = "Phone number must be between 7 and 14 characters")
        String phone,

        @Email(message = "Provide a valid email address")
        @NotBlank(message = "Email cannot be blank")
        @NotNull(message = "Filed cannot be null")
        String email,

        @Valid
        List<AddressRequestDto> address
) {

}
