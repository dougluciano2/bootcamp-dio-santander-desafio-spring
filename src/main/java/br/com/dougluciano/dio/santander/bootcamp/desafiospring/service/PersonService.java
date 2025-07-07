package br.com.dougluciano.dio.santander.bootcamp.desafiospring.service;

import br.com.dougluciano.dio.santander.bootcamp.desafiospring.dto.PersonRequestDto;
import br.com.dougluciano.dio.santander.bootcamp.desafiospring.dto.PersonResponseDto;
import br.com.dougluciano.dio.santander.bootcamp.desafiospring.model.Address;
import br.com.dougluciano.dio.santander.bootcamp.desafiospring.model.Person;
import br.com.dougluciano.dio.santander.bootcamp.desafiospring.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
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
    public PersonResponseDto findById(UUID id){

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

        mapAddressToPersist(request, person);

        var persist = repository.save(person);

        return new PersonResponseDto(persist);
    }

    private void mapAddressToPersist(PersonRequestDto request, Person person) {
        List<Address> addresses = request.address().stream().map(addressDto -> {
            var address = new Address();
            address.setStreet(addressDto.street());
            address.setNumber(addressDto.number());
            address.setComplement(addressDto.complement());
            address.setNeighborhood(addressDto.neighborhood());
            address.setCity(addressDto.city());
            address.setState(addressDto.state());
            address.setZipCode(addressDto.zipCode());
            address.setPerson(person);
            return address;
        }).collect(Collectors.toList());
    }


    @Transactional
    public PersonResponseDto update(UUID id, PersonRequestDto request){
       Person persist = repository.findById(id)
               .orElseThrow(() -> new NoSuchElementException("Person #ID " + id + " not found"));
       persist.setFullName(request.fullName());
       persist.setEmail(request.email());
       persist.setPhone(request.phone());

       /*
       limpar a lista de endereços e re-adicionar para não gerar lixo eletronico
       propriedade orphanRemoval=true na entidade permite isso.
        */
        persist.getAddresses().clear();

        List<Address> newAddresses = request.address()
                .stream()
                .map(addressDto -> {
                    var address = new Address();
                    address.setStreet(addressDto.street());
                    address.setNumber(addressDto.number());
                    address.setComplement(addressDto.complement());
                    address.setNeighborhood(addressDto.neighborhood());
                    address.setCity(addressDto.city());
                    address.setState(addressDto.state());
                    address.setZipCode(addressDto.zipCode());
                    address.setPerson(persist);
                    return address;
                }).collect(Collectors.toList());

        var persisted = repository.save(persist);

       return new PersonResponseDto(persisted);
    }

    @Transactional
    public void delete(UUID id){
        Person toDelete = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException());
        repository.delete(toDelete);
    }

}
