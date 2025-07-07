package br.com.dougluciano.dio.santander.bootcamp.desafiospring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "address")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Address extends AbstractFullEntity{

    @NotBlank(message = "Street cannot be blank")
    @Size(max = 255)
    @Column(name = "street")
    private String street;

    @NotBlank(message = "Number cannot be blank")
    @Size(max = 50)
    @Column(name = "number")
    private String number;

    @Size(max = 255) // Complemento é opcional, então não usamos @NotBlank
    @Column(name = "complement")
    private String complement;

    @NotBlank(message = "Neighborhood cannot be blank")
    @Size(max = 255)
    @Column(name = "neighborhood")
    private String neighborhood;

    @NotBlank(message = "City cannot be blank")
    @Size(max = 255)
    @Column(name = "city")
    private String city;

    @NotBlank(message = "State cannot be blank")
    @Size(min = 2, max = 2, message = "State must have 2 characters")
    @Column(name = "state")
    private String state;

    @NotBlank(message = "Zip code cannot be blank")
    @Size(max = 10, message = "Zip code must have up to 10 characters")
    @Column(name = "zipcode")
    private String zipCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personid", nullable = false)
    private Person person;

}
