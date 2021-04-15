package dev.neytor.backend.timespanner.territory.country.application.service;

import dev.neytor.backend.timespanner.common.UseCase;
import dev.neytor.backend.timespanner.territory.country.application.port.in.CountryQuery;
import dev.neytor.backend.timespanner.territory.country.application.port.out.CountryQueryPort;
import dev.neytor.backend.timespanner.territory.country.domain.Country;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@UseCase
public class CountryQueryService implements CountryQuery {

    private final CountryQueryPort queryPort;

    @Override
    public Optional<Country> findByAlpha3Code(String alpha3Code) {
        return queryPort.findCountryByAlpha3Code(alpha3Code);
    }

    @Override
    public List<Country> findByNameContaining(String name) {
        return queryPort.findByNameContaining(name);
    }

    @Override
    public List<Country> findAll() {
        return queryPort.findAll();
    }
}
