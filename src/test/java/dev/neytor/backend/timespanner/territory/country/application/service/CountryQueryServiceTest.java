package dev.neytor.backend.timespanner.territory.country.application.service;

import dev.neytor.backend.timespanner.common.CountryTestData;
import dev.neytor.backend.timespanner.territory.country.application.port.out.CountryQueryPort;
import dev.neytor.backend.timespanner.territory.country.domain.Country;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;

@DisplayName("Testing country query use case")
@ExtendWith(MockitoExtension.class)
class CountryQueryServiceTest {

    private final CountryQueryPort queryPort = mock(CountryQueryPort.class);

    @DisplayName("Success find by alpha 3 code return should have a valid country")
    @Test
    void findByAlpha3CodeSuccessOptionalReturnedMustHaveValidCountry() {
        CountryQueryService service = new CountryQueryService(queryPort);
        String givenCode = "NQP";

        when(queryPort.findCountryByAlpha3Code(any(String.class)))
                .thenReturn(Optional.of(CountryTestData.defaultCountry().build()));

        Optional<Country> returnedCountry = service.findByAlpha3Code(givenCode);

        verify(queryPort, atLeastOnce()).findCountryByAlpha3Code(any(String.class));
        assertTrue(returnedCountry.isPresent());
    }

    @DisplayName("Success find by name containing must return a not empty countries list")
    @Test
    void findByNameContainingSuccessMustReturnNonEmptyList() {
        CountryQueryService service = new CountryQueryService(queryPort);
        String givenPartialName = "Some";
        List<Country> countryList = getCountryList();

        when(queryPort.findByNameContaining(any(String.class)))
                .thenReturn(countryList);

        List<Country> returnedList = service.findByNameContaining(givenPartialName);

        verify(queryPort, atLeastOnce()).findByNameContaining(any(String.class));
        assertTrue(!returnedList.isEmpty());
    }

    @DisplayName("Success find all must return a not empty countries list")
    @Test
    void findAllSuccessMustReturnNonEmptyList() {
        CountryQueryService service = new CountryQueryService(queryPort);
        List<Country> countryList = getCountryList();

        when(queryPort.findAll()).thenReturn(countryList);

        List<Country> returnedList = service.findAll();

        verify(queryPort, atLeastOnce()).findAll();
        assertTrue(!returnedList.isEmpty());
    }

    private List<Country> getCountryList() {
        return CountryTestData.getCountries();
    }
}