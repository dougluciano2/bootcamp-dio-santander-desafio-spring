package br.com.dougluciano.dio.santander.bootcamp.desafiospring.service;

import br.com.dougluciano.dio.santander.bootcamp.desafiospring.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    private final UserRepository repository;

    public AuthenticationService(UserRepository repository){this.repository = repository;}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = repository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        // --- ADICIONE ESTAS LINHAS PARA DEPURAÇÃO ---
        System.out.println("--- USUÁRIO ENCONTRADO NO BANCO ---");
        System.out.println("Username: " + user.getUsername());
        System.out.println("Password Hash (do banco): " + user.getPassword());
        System.out.println("------------------------------------");
        // -----------------------------------------
        return user;
    }
}
