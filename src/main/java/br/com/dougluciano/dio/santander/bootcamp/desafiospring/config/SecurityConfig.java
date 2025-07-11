package br.com.dougluciano.dio.santander.bootcamp.desafiospring.config;

import br.com.dougluciano.dio.santander.bootcamp.desafiospring.filter.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private static final String[] WHITELIST_NO_AUTH_REQUIRED = {
            "/login", "/docs", "/swagger-ui/**", "/v3/api-docs/**"
    };

    @Autowired
    private SecurityFilter securityFilter;

    public SecurityConfig(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        return http
                /*
                 desabilita o cross site request forgery que evita ações maliciosas no navegador sem o consentimento do usuário
                 e o uso de sessões e cookies, sendo uma API stateless, não precisamos disso
                 */
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())

                // configura a gestão de conexão como STATELESS, ou seja, sem guardar sessão
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // regras de autorização para endipoints
                .authorizeHttpRequests(auth -> auth
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

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();

        /*
         Permite requisições de qualquer origem para o Swagger funcionar. Bom para ambiente de homologação
         Em produção, é bom restringir somente ao front-end.
         */
        configuration.setAllowedOrigins(List.of("*"));
        /*
         * Permite requisições de todos os métodos HTTP para o swagger funcionar.
         */
        configuration.setAllowedMethods(List.of(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "OPTIONS",
                "HEAD",
                "TRACE",
                "CONNECT"
                ));
        /*
         * Permite todos os cabeçalhos
         */
        configuration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // aplica a configuração do CORS para os paths da aplicação
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }


}
