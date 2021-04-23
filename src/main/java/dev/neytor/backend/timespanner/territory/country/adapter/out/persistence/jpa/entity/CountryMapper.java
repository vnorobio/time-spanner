package dev.neytor.backend.timespanner.territory.country.adapter.out.persistence.jpa.entity;

import dev.neytor.backend.timespanner.territory.country.domain.Country;
import org.springframework.stereotype.Component;

@Component
public class CountryMapper {

    public Country toDomain(CountryEntity entity) {
        return Country.withId(
                new Country.CountryId(entity.getId()),
                entity.getNumericCode(),
                entity.getAlpha3Code(),
                entity.getName()
        );
    }

    public CountryEntity toJpaEntity(Country country) {
        return new CountryEntity(
                country.getId().orElse(null).getValue(),
                country.getAlpha3Code(),
                country.getName(),
                country.getNumericCode()
        );
    }
}
