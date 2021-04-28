package dev.neytor.backend.timespanner.territory.country.application.port.in.command.model;

import dev.neytor.backend.timespanner.territory.country.application.port.in.command.CreateCountryCommand;
import dev.neytor.backend.timespanner.territory.country.application.port.in.command.UpdateCountryCommand;
import dev.neytor.backend.timespanner.territory.country.domain.Country;
import org.springframework.stereotype.Component;

@Component
public class CountryCommandMapper {

    public Country toDomain(CreateCountryCommand command){
        return Country.withoutId(
                command.getData().getNumericCode(),
                command.getData().getAlpha3Code(),
                command.getData().getName()
        );
    }

    public Country toDomain(UpdateCountryCommand command){
        return Country.withId(
                new Country.CountryId(command.getId()),
                command.getData().getNumericCode(),
                command.getData().getAlpha3Code(),
                command.getData().getName()
        );
    }
}
