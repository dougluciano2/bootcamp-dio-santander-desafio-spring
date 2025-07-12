
# Desafio  de projeto Bootcamp DIO/Santander: Publicando Sua API REST na Nuvem Usando Spring Boot 3, Java 17 e Railway


**Desenvolvido por:** [dougluciano2](https://github.com/dougluciano2)

## 📫 Como me encontrar

[![LinkedIn](https://img.shields.io/badge/LinkedIn-DouglasLuciano-blue?style=for-the-badge&logo=linkedin)](https://www.linkedin.com/in/douglasluciano/)
[![GitHub](https://img.shields.io/badge/GitHub-douglasluciano-black?style=for-the-badge&logo=github)](https://github.com/douglasluciano)
[![Portfólio](https://img.shields.io/badge/Portf%C3%B3lio-GitHub%20Pages-blueviolet?style=for-the-badge&logo=github)](https://dougluciano2.github.io)


# Spring Boot Java API: Gerenciamento de Pessoas e Endereços

[![Java](https://img.shields.io/badge/Java-21-007396?style=for-the-badge&logo=openjdk)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.x-6DB33F?style=for-the-badge&logo=spring)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.9-C60F13?style=for-the-badge&logo=apache-maven)](https://maven.apache.org/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-336791?style=for-the-badge&logo=postgresql)](https://www.postgresql.org/)
[![Swagger UI](https://img.shields.io/badge/Swagger%20UI-2.x-85EA2D?style=for-the-badge&logo=swagger)](https://swagger.io/tools/swagger-ui/)
[![Google Cloud](https://img.shields.io/badge/Google%20Cloud-34A853?style=for-the-badge&logo=google-cloud)](https://cloud.google.com/)

Este projeto é uma API RESTful desenvolvida com Spring Boot Java, com o objetivo de gerenciar informações de pessoas (nome completo, telefone, e-mail) e seus respectivos endereços. Ele utiliza um banco de dados PostgreSQL e expõe seus endpoints através de uma interface Swagger UI para facilitar a documentação e testes. O deploy desta aplicação é automatizado no Google Cloud Run utilizando GitHub Actions para CI/CD.

## Diagramas de Classe UML

Abaixo estão os diagramas UML das principais classes do projeto. Eles representam as entidades de domínio, os Data Transfer Objects (DTOs), e as camadas de serviço e controle.


<b>Diagrama de Classes</b>

  ```mermaid
classDiagram
    direction LR

    %% --- Entidades de Domínio ---
    class Person {
        -UUID id
        -String fullName
        -List~Address~ addresses
    }
    class Address {
        -UUID id
        -String street
        -Person person
    }
    class User {
        -UUID id
        -String userName
        -String password
        -Set~Role~ roles
    }
    class Role {
        <<Enumeration>>
        USER
        ADMIN
    }

    %% --- Camada de Controle ---
    class PersonController {
        -PersonService personService
    }
    class AuthenticationController {
        -AuthenticationManager authManager
        -TokenService tokenService
        +login(AuthenticationRequestDto) ResponseEntity
    }

    %% --- Camada de Serviço ---
    class PersonService {
        -PersonRepository personRepository
    }
    class TokenService {
        +generateToken(Authentication) String
        +getSubject(String) String
    }
    class AuthenticationService {
        <<UserDetailsService>>
        -UserRepository userRepository
        +loadUserByUsername(String) UserDetails
    }
    
    %% --- Camada de Repositório ---
    class PersonRepository {
        <<Interface>>
        +JpaRepository~Person, UUID~
    }
    class AddressRepository {
        <<Interface>>
        +JpaRepository~Address, UUID~
    }
    class UserRepository {
        <<Interface>>
        +findByUserName(String) UserDetails
    }

    %% --- Filtro de Segurança ---
    class SecurityFilter {
        -TokenService tokenService
        -AuthenticationService authService
    }

    %% --- Relacionamentos de Domínio ---
    Person "1" -- "0..*" Address : has
    User "1" -- "1..*" Role : has

    %% --- Fluxo de Negócio e Dependências ---
    PersonController ..> PersonService : uses
    PersonService ..> PersonRepository : uses
    PersonRepository --> Person : manages
    AddressRepository --> Address : manages
    
    %% --- Fluxo de Segurança ---
    AuthenticationController ..> AuthenticationManager : authenticates
    AuthenticationController ..> TokenService : generates token
    SecurityFilter ..> TokenService : validates token
    SecurityFilter ..> AuthenticationService : loads user
    AuthenticationService ..> UserRepository : finds user
    UserRepository --> User : manages
```



## Tecnologias Utilizadas

- **Java 21:** Linguagem de programação principal.
- **Spring Boot 3.3.1:** Framework para desenvolvimento rápido de aplicações Java.
- **Spring Data JPA & Hibernate:** Para persistência de dados.
- **Spring Web:** Para a construção da API RESTful.
- **Spring Security:** Para segurança (autenticação e autorização).
- **Maven:** Gerenciamento de dependências e build.
- **PostgreSQL 16:** Banco de dados relacional.
- **JWT (JSON Web Tokens):** Para autenticação stateless.
- **Swagger UI (SpringDoc OpenAPI):** Para documentação interativa da API.
- **Google Cloud Run, Cloud SQL & Artifact Registry:** Plataforma de deploy e serviços na nuvem.
- **GitHub Actions:** CI/CD para automação de build e deploy.
- **Docker:** Plataforma de containerização.

## Execução remota na nuvem:

1. **Swagger UI**

O Acesso via swagger está disponível em:
https://desafio-spring-api-979569748830.us-central1.run.app/docs

2. **Postman e outras ferramentas com esse destino**

Endpoints:

https://desafio-spring-api-979569748830.us-central1.run.app/login (Não requer autenticação)

https://desafio-spring-api-979569748830.us-central1.run.app/persons (Requer autenticação em todos os métodos)

Usuários:

admin - agesune2 (Role de admin)

user - agesune1 (Role de user)


## Execução Local

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/dougluciano2/bootcamp-dio-santander-desafio-spring.git](https://github.com/dougluciano2/bootcamp-dio-santander-desafio-spring.git)
    cd bootcamp-dio-santander-desafio-spring
    ```

2.  **Inicie o Banco de Dados Local:**
    Um arquivo `docker-compose.yml` está incluído para iniciar um container PostgreSQL.
    ```bash
    docker-compose up -d
    ```
    OBSERVAÇÃO: As migrations do flyway precisaram ser retiradas para o deploy no Google Cloud, em caso de execução local da imagem, buscar commits anteriores que ainda tem o flyway e os scripts de migração para criação de usuários para utilização dos recursos.

3.  **Execute a Aplicação:**
    Em execução local, projeto está configurado para se conectar ao banco de dados provisionado na nuvem, será necessário configurar o banco para apontar para a imagem docker do banco ou para o banco instalado localmente.
    ```bash
    mvn spring-boot:run
    ```

    A aplicação estará disponível em `http://localhost:8080`.

4.  **Acesse a Documentação:**
    Abra seu navegador em `http://localhost:8080/docs`.

## Deploy no Google Cloud

O deploy no Google Cloud Run é automatizado através do GitHub Actions sempre que um commit é feito na branch `main`. Para que a pipeline funcione, os seguintes segredos devem ser configurados no repositório do GitHub (`Settings > Secrets and variables > Actions`):

- `GCP_PROJECT_ID`
- `GCP_SA_EMAIL`
- `GCP_WIF_PROVIDER`
- `GCP_INSTANCE_CONNECTION_NAME`
- `DB_USERNAME_PROD`
- `DB_PASSWORD_PROD`
- `API_SECURITY_TOKEN_SECRET`

Após um push na `main`, o GitHub Actions irá:

1.  Construir a imagem Docker.
2.  Enviar a imagem para o Google Artifact Registry.
3.  Realizar o deploy de uma nova revisão para o Google Cloud Run.

A URL da aplicação pode ser encontrada no Google Cloud Console, na seção do serviço Cloud Run.

## Endpoints da API

A documentação completa dos endpoints, com exemplos, está disponível no Swagger UI (`/docs`). Os principais são:

- `POST /login`: Autenticação para obter um token JWT.
- `GET /persons`: Retorna todas as pessoas (requer role `USER` ou `ADMIN`).
- `POST /persons`: Cria uma nova pessoa com seus endereços (requer role `ADMIN`).
- `DELETE /persons/{id}`: Exclui uma pessoa (requer role `ADMIN`).

## JSON Schema Login

URL: https://desafio-spring-api-979569748830.us-central1.run.app/login

````json
{
  "userName": "",
  "password": ""
}
````

## JSON Schema Person (POST AND PUT)

URL: https://desafio-spring-api-979569748830.us-central1.run.app/persons

````json
{
  "fullName" : "string",
  "phone" : "string",
  "email" : "string",
  "address": [
    {
      "street": "string",
      "number": "string",
      "complement": "string",
      "neighborhood": "string",
      "city": "string",
      "state": "string",
      "zipCode": "string"
    }
  ]
}
````

## JSON Schema Person (GET)

URL: https://desafio-spring-api-979569748830.us-central1.run.app/persons

````json
[
  {
    "id": "UUID",
    "fullName": "string",
    "phone": "string",
    "email": "string",
    "addresses": [
      {
        "id": "UUID",
        "fullQualifiedAddress": "string"
      }
    ]
  }
]
````