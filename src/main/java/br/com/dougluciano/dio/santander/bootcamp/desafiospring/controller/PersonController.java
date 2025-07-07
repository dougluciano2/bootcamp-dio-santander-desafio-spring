package br.com.dougluciano.dio.santander.bootcamp.desafiospring.controller;

import br.com.dougluciano.dio.santander.bootcamp.desafiospring.dto.PersonRequestDto;
import br.com.dougluciano.dio.santander.bootcamp.desafiospring.dto.PersonResponseDto;
import br.com.dougluciano.dio.santander.bootcamp.desafiospring.model.Person;
import br.com.dougluciano.dio.santander.bootcamp.desafiospring.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonService service;

    public PersonController(PersonService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PersonResponseDto>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonResponseDto> findById(@PathVariable UUID id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<PersonResponseDto> create(@Valid @RequestBody PersonRequestDto request){
        var persist = service.create(request);

        URI uriLocation = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(persist.id())
                .toUri();

        return ResponseEntity.created(uriLocation).body(persist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonResponseDto> update(@PathVariable UUID id, @Valid @RequestBody PersonRequestDto request){
        var update = service.update(id, request);

        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

}
