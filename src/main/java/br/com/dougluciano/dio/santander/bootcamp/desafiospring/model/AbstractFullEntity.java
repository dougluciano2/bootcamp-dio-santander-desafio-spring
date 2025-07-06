package br.com.dougluciano.dio.santander.bootcamp.desafiospring.model;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
abstract class AbstractFullEntity extends AbstractEntity {

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean inUse = true;

    @PrePersist
    public void onPrePersist(){
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void onPreUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
}
