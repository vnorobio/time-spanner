package dev.neytor.backend.timespanner.territory.country.adapter.in.web.dto;

import dev.neytor.backend.timespanner.territory.country.domain.Country;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
public class DtoDataFormatter {
    public CountryDto toDto(@NotNull Country country){
        return new CountryDto(
                country.getId().map(Country.CountryId::getValue).orElse(null),
                country.getAlpha3Code(),
                country.getName(),
                country.getNumericCode()
        );
    }
}
