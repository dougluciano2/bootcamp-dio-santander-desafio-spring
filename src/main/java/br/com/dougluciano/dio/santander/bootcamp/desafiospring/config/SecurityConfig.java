package br.com.dougluciano.dio.santander.bootcamp.desafiospring.config;

import br.com.dougluciano.dio.santander.bootcamp.desafiospring.filter.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private static final String[] SWAGGER_WHITELIST = {
            "/docs", "/swagger-ui/**", "/v3/api-docs/**"
    };

    private static final String[] WHITELIST_NO_AUTH_REQUIRED = {
            "/login", "h2-console"
    };

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        return http
                /*
                 desabilita o cross site request forgery que evita ações maliciosas no navegador sem o consentimento do usuário
                 e o uso de sessões e cookies, sendo uma API stateless, não precisamos disso
                 */
                .csrf(csrf -> csrf.disable())

                // configura a gestão de conexão como STATELESS, ou seja, sem guardar sessão
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // regras de autorização para endipoints
                .authorizeHttpRequests(auth -> auth
                        // autorizando acesso sem autenticação para o swagger (Acesso público)
                        .requestMatchers(SWAGGER_WHITELIST).permitAll()
                        // autorizando acesso sem autenticação pela constante criada na classe
                        .requestMatchers(WHITELIST_NO_AUTH_REQUIRED).permitAll()
                        // para outros endpoints, será necessário autenticação
                        .anyRequest().authenticated()
                )
                // possibilita a autenticação via postman passando o usuario e senha pelo cabeçalho por ex.
                // 2. Adicione seu filtro para ser executado antes do filtro de autenticação padrão
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /*
        Cria o codificador de senha, o BCrypt e um padrão de mercado.
    */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


}
