package dev.neytor.backend.timespanner.territory.country.application.port.in.command;

import dev.neytor.backend.timespanner.territory.country.domain.Country;
import org.springframework.stereotype.Component;

@Component
public class CountryCommandMapper {

    private CountryCommandMapper() {
    }

    public Country toDomain(CreateCountryCommand command){
        return Country.withoutId(
                command.getNumericCode(),
                command.getAlpha3Code(),
                command.getName()
        );
    }

    public Country toDomain(UpdateCountryCommand command){
        return Country.withId(
                new Country.CountryId(command.getId()),
                command.getNumericCode(),
                command.getAlpha3Code(),
                command.getName()
        );
    }
}
