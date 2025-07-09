# Estágio 1: Build da Aplicação com Maven
# Usamos uma imagem que já vem com Maven e JDK 21
FROM maven:3.9-eclipse-temurin-21 AS build

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia primeiro o pom.xml para aproveitar o cache de dependências do Docker
COPY pom.xml .

# Baixa todas as dependências do projeto
RUN mvn dependency:go-offline

# Copia o restante do código fonte
COPY src ./src

# Executa o build do Maven, gerando o arquivo .jar (pulando os testes)
RUN mvn package -DskipTests


# Estágio 2: Imagem Final de Execução
# Usamos uma imagem JRE leve, apenas com o necessário para rodar Java
FROM eclipse-temurin:21-jre-jammy

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo .jar gerado no estágio de build para a imagem final
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta 8080, que o Spring usa por padrão
EXPOSE 8080

# Comando para executar a aplicação quando o container iniciar
ENTRYPOINT ["java", "-jar", "app.jar"]