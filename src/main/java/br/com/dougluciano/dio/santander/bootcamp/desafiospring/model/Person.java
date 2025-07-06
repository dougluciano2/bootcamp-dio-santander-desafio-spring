package br.com.dougluciano.dio.santander.bootcamp.desafiospring.model;

import jakarta.persistence.Entity;
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

    private String fullName;
    private String phone;
    private String email;
}
