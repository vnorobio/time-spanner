package dev.neytor.backend.timespanner.territory.country.adapter.out.persistence.jpa;

import dev.neytor.backend.timespanner.territory.country.adapter.out.persistence.jpa.entity.CountryMapper;
import dev.neytor.backend.timespanner.territory.country.application.port.out.CountryQueryPort;
import dev.neytor.backend.timespanner.territory.country.domain.Country;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CountryQueryAdapter implements CountryQueryPort {

    private final CountryRepository repository;
    private final CountryMapper mapper;

    @Override
    public Optional<Country> findCountryByAlpha3Code(String alpha3Code) {
        return Optional.ofNullable(
                repository.findByAlpha3Code(alpha3Code)
                        .map(mapper::toDomain)
                        .orElse(null));
    }

    @Override
    public Optional<Country> findByNumericCode(Integer numericCode) {
        return Optional.ofNullable(
                repository.findByNumericCode(numericCode)
                        .map(mapper::toDomain)
                        .orElse(null));
    }

    @Override
    public Optional<Country> findByName(String name) {
        return Optional.ofNullable(
                repository.findByName(name)
                        .map(mapper::toDomain)
                        .orElse(null));
    }

    @Override
    public List<Country> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Country> findByNameContaining(String name) {
        return repository.findByNameContaining(name)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean doesCountryIdExists(Long id) {
        return repository.existsById(id);
    }
}
