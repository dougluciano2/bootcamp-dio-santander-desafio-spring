package br.com.dougluciano.dio.santander.bootcamp.desafiospring.controller;

import br.com.dougluciano.dio.santander.bootcamp.desafiospring.model.Person;
import br.com.dougluciano.dio.santander.bootcamp.desafiospring.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonService service;

    public PersonController(PersonService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Person>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Person> create(@RequestBody Person person){
        return ResponseEntity.ok(service.create(person));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> update(@PathVariable Long id, @RequestBody Person person){
        return ResponseEntity.ok(service.update(id,person));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

}
