package br.com.dougluciano.dio.santander.bootcamp.desafiospring.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity()
@Table(name = "userssystem")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginAndCreateUser extends AbstractFullEntity{

    private String userName;
    private String password;
}
