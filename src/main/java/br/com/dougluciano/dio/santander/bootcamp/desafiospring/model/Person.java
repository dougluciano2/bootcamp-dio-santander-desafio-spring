package br.com.dougluciano.dio.santander.bootcamp.desafiospring.model;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Person extends AbstractFullEntity{

    @NotBlank(message = "Full name cannot be blank")
    private String fullName;

    @NotBlank(message = "Phone cannot be blank")
    private String phone;

    @Email(message = "Provide a valid email address")
    @NotBlank(message = "Email cannot be blank")
    private String email;
}
