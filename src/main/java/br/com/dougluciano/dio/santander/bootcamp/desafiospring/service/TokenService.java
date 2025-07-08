package br.com.dougluciano.dio.santander.bootcamp.desafiospring.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    private String issue = "DougCompanyEmissor";

    // injetando a chave criada
    @Value("${api.security.token-secret}")
    private String secretToken;

    private SecretKey getSigninKey(){
        return Keys.hmacShaKeyFor(secretToken.getBytes(StandardCharsets.UTF_8));
    }

    /*
    Definindo através de um método o período de duração do token, que será de 30 minutos
    no fuso horário de são paulo, brasil
     */

    private Date expiresAt(){
        return Date.from(LocalDateTime.now()
                .plusMinutes(30)
                .toInstant(ZoneOffset.of("-03:00")));
    }

    /*
    Gerando um token JWT para o usuário que for autenticado
     */

    public String generateToken(Authentication authentication){
        // objeto que representa o usuário logado
        UserDetails principal = (UserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .issuer(issue) // Emissor do token declarado no início da classe
                .subject(principal.getUsername()) // para qual usuário o token está sendo emitido
                .issuedAt(new Date()) //data de emissão do certificado
                .expiration(expiresAt()) // data/hora em que o token expira
                .signWith(getSigninKey()) // assina o token com o metodo getSigninKey() que criamos que leva chave
                .compact();
    }

    /*
    Extrai o nome de usuário que o subject de um token válido
     */

    public String getSubject(String token){
        Claims claims = Jwts.parser()
                .verifyWith(getSigninKey()) // verifica a validade do token pela chave secreta
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

}
