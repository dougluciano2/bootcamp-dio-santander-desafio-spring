package br.com.dougluciano.dio.santander.bootcamp.desafiospring.service;

import br.com.dougluciano.dio.santander.bootcamp.desafiospring.dto.PersonRequestDto;
import br.com.dougluciano.dio.santander.bootcamp.desafiospring.dto.PersonResponseDto;
import br.com.dougluciano.dio.santander.bootcamp.desafiospring.model.Person;
import br.com.dougluciano.dio.santander.bootcamp.desafiospring.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonRepository repository;

    public PersonService(PersonRepository repository){
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<PersonResponseDto> findAll(){

        return repository.findAll().stream()
                .map(PersonResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PersonResponseDto findById(Long id){

        return repository.findById(id)
                .map(PersonResponseDto::new)
                .orElseThrow(() -> new NoSuchElementException());
    }

    @Transactional
    public PersonResponseDto create(PersonRequestDto request){
        if (repository.existsByEmail(request.email())){
            throw new IllegalArgumentException("E-mail already in use.");
        }

        var person = new Person();
        person.setId(null);
        person.setFullName(request.fullName());
        person.setPhone(request.phone());
        person.setEmail(request.email());

        var persist = repository.save(person);

        return new PersonResponseDto(persist);
    }

    @Transactional
    public PersonResponseDto update(Long id, PersonRequestDto request){
       Person persist = repository.findById(id)
               .orElseThrow(() -> new NoSuchElementException("Person #ID " + id + " not found"));
       persist.setFullName(request.fullName());
       persist.setEmail(request.email());
       persist.setPhone(request.phone());

       return new PersonResponseDto(repository.save(persist));
    }

    @Transactional
    public void delete(Long id){
        Person toDelete = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException());
        repository.delete(toDelete);
    }

}
