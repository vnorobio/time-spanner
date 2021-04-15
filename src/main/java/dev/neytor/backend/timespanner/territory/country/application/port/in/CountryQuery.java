package dev.neytor.backend.timespanner.territory.country.application.port.in;

import dev.neytor.backend.timespanner.territory.country.domain.Country;

import java.util.List;
import java.util.Optional;

public interface CountryQuery {
    Optional<Country> findByAlpha3Code(String alpha3Code);

    List<Country> findByNameContaining(String name);

    List<Country> findAll();
}
