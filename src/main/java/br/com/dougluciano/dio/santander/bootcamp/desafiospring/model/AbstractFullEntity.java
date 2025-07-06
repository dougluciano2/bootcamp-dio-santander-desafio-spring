package br.com.dougluciano.dio.santander.bootcamp.desafiospring.model;

import java.time.LocalDateTime;

abstract class AbstractFullEntity extends AbstractEntity {

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean inUse;
}
