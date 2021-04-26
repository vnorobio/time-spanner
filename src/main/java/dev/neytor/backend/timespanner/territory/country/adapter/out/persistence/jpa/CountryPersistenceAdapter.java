package dev.neytor.backend.timespanner.territory.country.adapter.out.persistence.jpa;

import dev.neytor.backend.timespanner.territory.country.adapter.out.persistence.jpa.entity.CountryMapper;
import dev.neytor.backend.timespanner.territory.country.application.port.out.CountryEstateManagerPort;
import dev.neytor.backend.timespanner.territory.country.domain.Country;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CountryPersistenceAdapter implements CountryEstateManagerPort {

    private final CountryRepository repository;

    private final CountryMapper mapper;

    @Override
    public Country createCountry(Country country) {
        return mapper.toDomain(repository.save( mapper.toJpaEntity(country)));
    }

    @Override
    public Country updateCountry(Country country) {
        return createCountry(country);
    }

    @Override
    public Boolean deleteCountryById(Long id) {
        repository.deleteById(id);
        return !repository.existsById(id);
    }

}
