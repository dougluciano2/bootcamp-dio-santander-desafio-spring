package br.com.dougluciano.dio.santander.bootcamp.desafiospring.filter;

import br.com.dougluciano.dio.santander.bootcamp.desafiospring.repository.UserRepository;
import br.com.dougluciano.dio.santander.bootcamp.desafiospring.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1 - Recupera o token que vem no cabeçalho da requisição
        var tokenJwt = recoverToken(request);

        if (tokenJwt != null){
            //2 valida o token e extrai o nome do usuário
            var subject = tokenService.getSubject(tokenJwt);

            // 3 verifica se o usuário existe na base de dados
            var user = userRepository.findByUserName(subject);

            if (user != null){
                //4 - Cria um objeto de autenticação para o spring security
                var authentication = new UsernamePasswordAuthenticationToken(user,null, user.getAuthorities());

                // 5 - define que o usuario está autenticado no contexto de segurança para a requisição
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        }

        // continua o fluxo da requisição
        filterChain.doFilter(request,response);
    }

    private String recoverToken(HttpServletRequest request){
        // pega o cabeçalho da requisição no header Authorization
        var authorizationHeader = request.getHeader("Authorization");
        // se o token não for nulo, então retira o "Bearer" do token
        if (authorizationHeader != null){
            // o token vem depois de "Bearer"
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
}
