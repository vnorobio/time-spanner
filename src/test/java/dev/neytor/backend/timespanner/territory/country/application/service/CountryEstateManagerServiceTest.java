package dev.neytor.backend.timespanner.territory.country.application.service;

import dev.neytor.backend.timespanner.common.CountryTestData;
import dev.neytor.backend.timespanner.territory.country.application.port.in.command.CountryCommandMapper;
import dev.neytor.backend.timespanner.territory.country.application.port.in.command.CreateCountryCommand;
import dev.neytor.backend.timespanner.territory.country.application.port.in.command.UpdateCountryCommand;
import dev.neytor.backend.timespanner.territory.country.application.port.out.CountryEstateManagerPort;
import dev.neytor.backend.timespanner.territory.country.domain.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.when;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

@DisplayName("Testing country estate manager use case")
@ExtendWith(MockitoExtension.class)
class CountryEstateManagerServiceTest {

    @InjectMocks
    private CountryEstateManagerService service;

    @Mock
    private CountryEstateManagerPort estateManagerPort;

    @Mock
    private CountryCommandMapper countryMapper;

    @Captor
    ArgumentCaptor<Country> countryArgumentCaptor;

    @BeforeEach
    public void setup(){
        this.service = new CountryEstateManagerService(estateManagerPort, countryMapper);
    }

    @DisplayName("Successful country creation should invoke create country method on port")
    @Test
    void createCountrySuccessShouldInvokeCreateCountryOnPort() {
        Country givenCountry = getDefaultCountryWithoutId();
        CreateCountryCommand givenCommand = getCreateCommandFromCountry(givenCountry);

        when(countryMapper.toDomain(any(CreateCountryCommand.class))).thenReturn(givenCountry);
        when(estateManagerPort.createCountry(any(Country.class))).thenReturn(getDefaultCountryWithId());

        Country savedCountry = service.createCountry(givenCommand);

        verify(countryMapper, atLeastOnce()).toDomain(any(CreateCountryCommand.class));
        verify(estateManagerPort, atLeastOnce()).createCountry(any(Country.class));
        assertThat(savedCountry).isNotNull();
    }

    @DisplayName("Successful country deletion should invoke delete by id method on port")
    @Test
    void deleteCountryByIdSuccessShouldInvokeDeleteByIdOnPort() {
        Long givenId = 1L;

        when(estateManagerPort.deleteCountryById(any(Long.class))).thenReturn(Boolean.TRUE);

        Boolean wasDeleted = service.deleteCountryById(givenId);

        verify(estateManagerPort, atLeastOnce()).deleteCountryById(any(Long.class));
        assertThat(wasDeleted).isTrue();
    }

    @DisplayName("Successful country update should invoke update method on port")
    @Test
    void updateCountrySuccessShouldInvokeUpdateCountryPort() {
        Country givenCountry = getDefaultCountryWithId();
        UpdateCountryCommand givenCommand = getUpdateCommandFromCountry(givenCountry);

        when(countryMapper.toDomain(any(UpdateCountryCommand.class))).thenReturn(givenCountry);
        when(estateManagerPort.updateCountry(any(Country.class))).thenReturn(getDefaultCountryWithId());

        Country updatedCountry = service.updateCountry(givenCommand);

        verify(countryMapper, atLeastOnce()).toDomain(any(UpdateCountryCommand.class));
        verify(estateManagerPort, atLeastOnce()).updateCountry(countryArgumentCaptor.capture());
        assertThat(countryArgumentCaptor.getValue()).isNotNull();
        assertThat(updatedCountry).isNotNull();
    }

    private CreateCountryCommand getCreateCommandFromCountry(Country country) {
        return new CreateCountryCommand(
                country.getNumericCode(),
                country.getAlpha3Code(),
                country.getName()
        );
    }

    private UpdateCountryCommand getUpdateCommandFromCountry(Country country) {
        return new UpdateCountryCommand(
                country.getId().orElse(new Country.CountryId(1L)).getValue(),
                country.getNumericCode(),
                country.getAlpha3Code(),
                country.getName()
        );
    }

    private Country getDefaultCountryWithoutId() {
        return CountryTestData.defaultCountry().withId(null).build();
    }

    private Country getDefaultCountryWithId() {
        return CountryTestData.defaultCountry().build();
    }
}