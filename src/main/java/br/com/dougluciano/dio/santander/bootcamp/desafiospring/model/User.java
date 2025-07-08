package br.com.dougluciano.dio.santander.bootcamp.desafiospring.model;

import br.com.dougluciano.dio.santander.bootcamp.desafiospring.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity()
@Table(name = "usersystem")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User extends AbstractFullEntity implements UserDetails {

    @Column(name = "username", unique = true)
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "roles")
    @Enumerated(EnumType.STRING)
    private Role roles;

    // spring retorna a lista de permissões ou roles do usuario
    // o spring security espera que as roles comecem com o prefixo "ROLE_".
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.roles.name()));
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    // definmos que a conta nunca expira
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // definimos que a conta nunca bloqueia
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // definimos que as credenciais nunca expiram
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // definimos que a conta está sempre habilitada
    @Override
    public boolean isEnabled() {
        return true;
    }
}
