package dev.neytor.backend.timespanner.territory.country.adapter.in.web.dto;

import dev.neytor.backend.timespanner.common.CountryTestData;
import dev.neytor.backend.timespanner.territory.country.domain.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testing DTO Data Formatter")
@ExtendWith(MockitoExtension.class)
class DtoDataFormatterTest {

    private DtoDataFormatter formatter;

    @BeforeEach
    void setUp() {
        this.formatter = new DtoDataFormatter();
    }

    @DisplayName("Success result CountryDto Should have same data as Country")
    @Test
    void successToDtoConvertShouldHaveSameData() {
        Country givenCountry = CountryTestData.defaultCountry().build();
        CountryDto resultDto = formatter.toDto(givenCountry);
        assertThat(resultDto.getId()).isEqualTo(givenCountry.getId().get().getValue());
        assertThat(resultDto.getAlpha3Code()).isEqualTo(givenCountry.getAlpha3Code());
        assertThat(resultDto.getName()).isEqualTo(givenCountry.getName());
        assertThat(resultDto.getNumericCode()).isEqualTo(givenCountry.getNumericCode());
    }
}