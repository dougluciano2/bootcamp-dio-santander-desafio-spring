package br.com.dougluciano.dio.santander.bootcamp.desafiospring.dto;

import br.com.dougluciano.dio.santander.bootcamp.desafiospring.model.Address;

import java.util.UUID;

public record AddressResponseDto(
        UUID id,
        String fullQualifiedAddress
) {
    public AddressResponseDto(Address model) {


        this(model.getId(), buildFullQualidiedAddress(model));
    }

    private static String buildFullQualidiedAddress(Address model){
        StringBuilder addressBuilder = new StringBuilder();
        addressBuilder
                .append("Logradouro: ").append(model.getStreet())
                .append(", número: ").append(model.getNumber());

        if (model.getComplement() != null && !model.getComplement().isBlank()){
            addressBuilder.append(", complemento: ").append(model.getComplement());
        } else {
            addressBuilder.append(", complemento: ").append("N/A");
        }

        addressBuilder.append(", cidade: ").append(model.getCity()).append("-").append(model.getState());
        addressBuilder.append(", CEP: ").append(model.getZipCode());

        return addressBuilder.toString();
    }

}
