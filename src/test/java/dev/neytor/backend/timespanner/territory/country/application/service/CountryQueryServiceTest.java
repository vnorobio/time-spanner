package dev.neytor.backend.timespanner.territory.country.application.service;

import dev.neytor.backend.timespanner.common.CountryTestData;
import dev.neytor.backend.timespanner.territory.country.application.port.out.CountryQueryPort;
import dev.neytor.backend.timespanner.territory.country.domain.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.when;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

@DisplayName("Testing country query use case")
@ExtendWith(MockitoExtension.class)
class CountryQueryServiceTest {

    @InjectMocks
    private CountryQueryService service;

    @Mock
    private CountryQueryPort queryPort;

    @BeforeEach
    public void setup(){
        this.service = new CountryQueryService(queryPort);
    }

    @DisplayName("Success find by alpha 3 code return should have a valid country")
    @Test
    void findByAlpha3CodeSuccessOptionalReturnedMustHaveValidCountry() {
        String givenCode = "NQP";

        when(queryPort.findCountryByAlpha3Code(any(String.class)))
                .thenReturn(Optional.of(CountryTestData.defaultCountry().build()));

        Optional<Country> returnedCountry = service.findByAlpha3Code(givenCode);

        verify(queryPort, atLeastOnce()).findCountryByAlpha3Code(any(String.class));
        assertThat(returnedCountry.isPresent()).isTrue();
    }

    @DisplayName("Success find by name containing must return a not empty countries list")
    @Test
    void findByNameContainingSuccessMustReturnNonEmptyList() {
        String givenPartialName = "Some";
        List<Country> countryList = getCountryList();

        when(queryPort.findByNameContaining(any(String.class)))
                .thenReturn(countryList);

        List<Country> returnedList = service.findByNameContaining(givenPartialName);

        verify(queryPort, atLeastOnce()).findByNameContaining(any(String.class));
        assertThat(returnedList).hasAtLeastOneElementOfType(Country.class);
    }

    @DisplayName("Success find all must return a not empty countries list")
    @Test
    void findAllSuccessMustReturnNonEmptyList() {
        List<Country> countryList = getCountryList();

        when(queryPort.findAll()).thenReturn(countryList);

        List<Country> returnedList = service.findAll();

        verify(queryPort, atLeastOnce()).findAll();
        assertThat(returnedList).hasAtLeastOneElementOfType(Country.class);
    }

    private List<Country> getCountryList() {
        return CountryTestData.getCountries();
    }
}