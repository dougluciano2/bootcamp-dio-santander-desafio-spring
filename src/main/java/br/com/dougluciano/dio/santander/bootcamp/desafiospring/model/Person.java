package br.com.dougluciano.dio.santander.bootcamp.desafiospring.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotNull(message = "Filed cannot be null")
    @NotNull
    @Size(min = 1, max = 200, message = "Name value must be between 1 and 150 characters")
    @Column(name = "fullname")
    private String fullName;

    @NotBlank(message = "Phone cannot be blank")
    @NotNull(message = "Filed cannot be null")
    @Size(min = 7, max = 14, message = "Phone number must be between 7 and 14 characters")
    @Column(name = "phone")
    private String phone;

    @Email(message = "Provide a valid email address")
    @NotBlank(message = "Email cannot be blank")
    @NotNull(message = "Filed cannot be null")
    @Column(name = "email")
    private String email;
}
