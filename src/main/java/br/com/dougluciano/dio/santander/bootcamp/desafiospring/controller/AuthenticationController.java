package br.com.dougluciano.dio.santander.bootcamp.desafiospring.controller;

import br.com.dougluciano.dio.santander.bootcamp.desafiospring.dto.AuthenticationRequestDto;
import br.com.dougluciano.dio.santander.bootcamp.desafiospring.dto.TokenResponseDto;
import br.com.dougluciano.dio.santander.bootcamp.desafiospring.model.User;
import br.com.dougluciano.dio.santander.bootcamp.desafiospring.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenResponseDto> login(@RequestBody @Valid AuthenticationRequestDto request){
        var authenticationToken = new UsernamePasswordAuthenticationToken(request.userName(), request.password());

        var authentication = manager.authenticate(authenticationToken);


        var tokenJwt = tokenService.generateToken(authentication);

        return ResponseEntity.ok(new TokenResponseDto(tokenJwt));
    }
}
