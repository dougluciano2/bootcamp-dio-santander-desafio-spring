package br.com.dougluciano.dio.santander.bootcamp.desafiospring.dto;

import java.time.LocalDateTime;

public record ApiErrorDto(
        LocalDateTime timestamp,
        Integer status,
        String error,
        String message,
        String path
) {
}
