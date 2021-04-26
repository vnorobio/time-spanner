package dev.neytor.backend.timespanner.territory.country.application.port.out;

import dev.neytor.backend.timespanner.territory.country.domain.Country;

import java.util.List;
import java.util.Optional;

public interface CountryQueryPort {
    Optional<Country> findCountryByAlpha3Code(String alpha3Code);

    Optional<Country> findByNumericCode(Integer numericCode);

    Optional<Country> findByName(String name);

    List<Country> findAll();

    List<Country> findByNameContaining(String name);

    Boolean doesCountryIdExists(Long id);
}
