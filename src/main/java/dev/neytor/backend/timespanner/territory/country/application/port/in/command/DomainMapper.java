package dev.neytor.backend.timespanner.territory.country.application.port.in.command;

import dev.neytor.backend.timespanner.territory.country.domain.Country;

public class DomainMapper {
    public static Country toDomain(CreateCountryCommand command){
        return Country.withoutId(
                command.getNumericCode(),
                command.getAlpha3Code(),
                command.getName(),
                command.getDivisionsDisplayName(),
                command.getSubDivisionsDisplayName()
        );
    }

    public static Country toDomain(UpdateCountryCommand command){
        return Country.withId(
                new Country.CountryId(command.getId()),
                command.getNumericCode(),
                command.getAlpha3Code(),
                command.getName(),
                command.getDivisionsDisplayName(),
                command.getSubDivisionsDisplayName()
        );
    }
}
