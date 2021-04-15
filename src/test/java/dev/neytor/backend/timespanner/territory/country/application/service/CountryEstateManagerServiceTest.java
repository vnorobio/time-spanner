package dev.neytor.backend.timespanner.territory.country.application.service;

import dev.neytor.backend.timespanner.common.CountryTestData;
import dev.neytor.backend.timespanner.territory.country.application.port.in.command.CreateCountryCommand;
import dev.neytor.backend.timespanner.territory.country.application.port.in.command.UpdateCountryCommand;
import dev.neytor.backend.timespanner.territory.country.application.port.out.CountryEstateManagerPort;
import dev.neytor.backend.timespanner.territory.country.domain.Country;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;

@DisplayName("Testing country estate manager use case")
@ExtendWith(MockitoExtension.class)
class CountryEstateManagerServiceTest {

    private final CountryEstateManagerPort estateManagerPort = mock(CountryEstateManagerPort.class);

    @DisplayName("Successful country creation should invoke create country method on port")
    @Test
    void createCountrySuccessShouldInvokeCreateCountryOnPort() {
        CountryEstateManagerService service = new CountryEstateManagerService(estateManagerPort);
        Country givenCountry = getDefaultCountryWithoutId();
        CreateCountryCommand givenCommand = getCreateCommandFromCountry(givenCountry);

        when(estateManagerPort.createCountry(any(Country.class))).thenReturn(getDefaultCountryWithId());

        Country savedCountry = service.createCountry(givenCommand);

        verify(estateManagerPort, atLeastOnce()).createCountry(any(Country.class));
        assertNotNull(savedCountry);
    }

    @DisplayName("Successful country deletion should invoke delete by id method on port")
    @Test
    void deleteCountryByIdSuccessShouldInvokeDeleteByIdOnPort() {
        CountryEstateManagerService service = new CountryEstateManagerService(estateManagerPort);
        Long givenId = 1L;

        when(estateManagerPort.deleteCountryById(any(Long.class))).thenReturn(Boolean.TRUE);

        Boolean wasDeleted = service.deleteCountryById(givenId);

        verify(estateManagerPort, atLeastOnce()).deleteCountryById(any(Long.class));
        assertTrue(wasDeleted);
    }

    @DisplayName("Successful country update should invoke update method on port")
    @Test
    void updateCountrySuccessShouldInvokeUpdateCountryPort() {
        CountryEstateManagerService service = new CountryEstateManagerService(estateManagerPort);
        Country givenCountry = getDefaultCountryWithId();
        UpdateCountryCommand givenCommand = getUpdateCommandFromCountry(givenCountry);

        when(estateManagerPort.updateCountry(any(Country.class))).thenReturn(getDefaultCountryWithId());

        Country updatedCountry = service.updateCountry(givenCommand);

        verify(estateManagerPort, atLeastOnce()).updateCountry(any(Country.class));
        assertNotNull(updatedCountry);
    }

    private CreateCountryCommand getCreateCommandFromCountry(Country country) {
        return new CreateCountryCommand(
                country.getNumericCode(),
                country.getAlphaCode3(),
                country.getName(),
                country.getDivisionsDisplayName(),
                country.getSubDivisionsDisplayName()
        );
    }

    private UpdateCountryCommand getUpdateCommandFromCountry(Country country) {
        return new UpdateCountryCommand(
                country.getId().orElse(new Country.CountryId(1L)).getValue(),
                country.getNumericCode(),
                country.getAlphaCode3(),
                country.getName(),
                country.getDivisionsDisplayName(),
                country.getSubDivisionsDisplayName()
        );
    }

    private Country getDefaultCountryWithoutId() {
        return CountryTestData.defaultCountry().withId(null).build();
    }

    private Country getDefaultCountryWithId() {
        return CountryTestData.defaultCountry().build();
    }
}