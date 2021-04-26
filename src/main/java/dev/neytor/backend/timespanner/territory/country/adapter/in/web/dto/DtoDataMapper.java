package dev.neytor.backend.timespanner.territory.country.adapter.in.web.dto;

import dev.neytor.backend.timespanner.territory.country.domain.Country;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
public class DtoDataMapper {
    public CountryDto toDto(@NotNull Country country){
        return new CountryDto(
                country.getId().get().getValue(),
                country.getAlpha3Code(),
                country.getName(),
                country.getNumericCode()
        );
    }
}
