package dev.neytor.backend.timespanner.territory.country.application.port.in;

import dev.neytor.backend.timespanner.territory.country.application.port.in.command.CreateCountryCommand;
import dev.neytor.backend.timespanner.territory.country.application.port.in.command.UpdateCountryCommand;
import dev.neytor.backend.timespanner.territory.country.domain.Country;

public interface CountryEstateManagerUseCase {
    Country createCountry(CreateCountryCommand command);

    Boolean deleteCountryById(Long id);

    Country updateCountry(UpdateCountryCommand command);
}

