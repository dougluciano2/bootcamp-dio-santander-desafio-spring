package br.com.dougluciano.dio.santander.bootcamp.desafiospring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "person")
public class Person extends AbstractFullEntity{

    @NotBlank(message = "Full name cannot be blank")
    @Size(min = 1, max = 200, message = "Name value must be between 1 and 150 characters")
    @Column(name = "fullname")
    private String fullName;

    @NotBlank(message = "Phone cannot be blank")
    @Size(min = 7, max = 14, message = "Phone number must be between 7 and 14 characters")
    @Column(name = "phone")
    private String phone;

    @Email(message = "Provide a valid email address")
    @NotBlank(message = "Email cannot be blank")
    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Address> addresses = new ArrayList<>();
}
