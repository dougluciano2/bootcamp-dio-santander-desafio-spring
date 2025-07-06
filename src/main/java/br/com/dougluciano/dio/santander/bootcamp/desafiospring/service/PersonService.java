package br.com.dougluciano.dio.santander.bootcamp.desafiospring.service;

import br.com.dougluciano.dio.santander.bootcamp.desafiospring.model.Person;
import br.com.dougluciano.dio.santander.bootcamp.desafiospring.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository repository;

    public PersonService(PersonRepository repository){
        this.repository = repository;
    }

    public List<Person> findAll(){
        return repository.findAll();
    }

    public Person findById(Long id){
        return repository.findById(id).orElseThrow();
    }

    public Person create(Person person){
        return repository.save(person);
    }

    public Person update(Long id, Person person){
        return repository.findById(id).map(p ->{
            p.setFullName(person.getFullName());
            p.setEmail(person.getEmail());
            p.setPhone(person.getPhone());
            return repository.save(p);
        }).orElseGet(() ->{
            return repository.save(person);
        });
    }

    public void delete(Long id){
        Person toDelete = this.findById(id);
        repository.delete(toDelete);
    }

}
