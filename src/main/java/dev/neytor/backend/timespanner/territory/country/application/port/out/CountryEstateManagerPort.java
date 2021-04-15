package dev.neytor.backend.timespanner.territory.country.application.port.out;

import dev.neytor.backend.timespanner.territory.country.domain.Country;

public interface CountryEstateManagerPort {
    Country createCountry(Country country);

    Country updateCountry(Country country);

    Boolean deleteCountryById(Long id);
}
