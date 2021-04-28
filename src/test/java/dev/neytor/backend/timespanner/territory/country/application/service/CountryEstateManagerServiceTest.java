package dev.neytor.backend.timespanner.territory.country.application.service;

import dev.neytor.backend.timespanner.common.CountryTestData;
import dev.neytor.backend.timespanner.common.exception.BusinessVerificationException;
import dev.neytor.backend.timespanner.territory.country.application.port.in.command.model.CountryCommandMapper;
import dev.neytor.backend.timespanner.territory.country.application.port.in.command.model.CountryData;
import dev.neytor.backend.timespanner.territory.country.application.port.in.command.CreateCountryCommand;
import dev.neytor.backend.timespanner.territory.country.application.port.in.command.UpdateCountryCommand;
import dev.neytor.backend.timespanner.territory.country.application.port.out.CountryEstateManagerPort;
import dev.neytor.backend.timespanner.territory.country.application.port.out.CountryQueryPort;
import dev.neytor.backend.timespanner.territory.country.domain.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.when;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@DisplayName("Testing country estate manager use case")
@ExtendWith(MockitoExtension.class)
class CountryEstateManagerServiceTest {

    @Captor
    ArgumentCaptor<Country> countryArgumentCaptor;
    @InjectMocks
    private CountryEstateManagerService service;
    @Mock
    private CountryEstateManagerPort estateManagerAdapter;
    @Mock
    private CountryQueryPort queryAdapter;
    @Mock
    private CountryCommandMapper countryMapper;

    private static CreateCountryCommand getCreateCommandFromCountry(Country country) {
        return new CreateCountryCommand(
                new CountryData(
                        country.getNumericCode(),
                        country.getAlpha3Code(),
                        country.getName()
                )
        );
    }

    private static Stream<Arguments> provideRegistersForBusinessVerificationException() {
        return Stream.of(
                Arguments.of(CountryTestData.defaultCountry().withId(null).build(), "name"),
                Arguments.of(CountryTestData.defaultCountry().withId(null).withName("OtherName").withNumericCode(99).build(), "alpha 3 code"),
                Arguments.of(CountryTestData.defaultCountry().withId(null).withName("OtherName").withAlpha3Code("OtherAlpha3Code").build(), "numeric code")
        );
    }

    @BeforeEach
    public void setup() {
        this.service = new CountryEstateManagerService(estateManagerAdapter, queryAdapter, countryMapper);
    }

    @DisplayName("Successful country creation should invoke create country method on port")
    @Test
    void createCountrySuccessShouldInvokeCreateCountryOnPort() {
        Country givenCountry = getDefaultCountryWithoutId();
        CreateCountryCommand givenCommand = getCreateCommandFromCountry(givenCountry);
        when(countryMapper.toDomain(any(CreateCountryCommand.class))).thenReturn(givenCountry);
        when(estateManagerAdapter.createCountry(any(Country.class))).thenReturn(getDefaultCountryWithId());
        when(queryAdapter.findAll()).thenReturn(Arrays.asList());
        Country savedCountry = service.createCountry(givenCommand);
        verify(countryMapper, atLeastOnce()).toDomain(any(CreateCountryCommand.class));
        verify(estateManagerAdapter, atLeastOnce()).createCountry(any(Country.class));
        assertThat(savedCountry).isNotNull();
    }

    @DisplayName("Successful country deletion should invoke delete by id method on port")
    @Test
    void deleteCountryByIdSuccessShouldInvokeDeleteByIdOnPort() {
        Long givenId = 1L;
        when(queryAdapter.doesCountryIdExists(any(Long.class))).thenReturn(Boolean.TRUE);
        when(estateManagerAdapter.deleteCountryById(any(Long.class))).thenReturn(Boolean.TRUE);
        Boolean wasDeleted = service.deleteCountryById(givenId);
        verify(estateManagerAdapter, atLeastOnce()).deleteCountryById(any(Long.class));
        assertThat(wasDeleted).isTrue();
    }

    @DisplayName("Fail delete country id that does not exists should throws BusinessVerificationException")
    @Test
    void deletingCountryByIdThatDoesNotExistsShouldThrowsBusinessVerificationException() {
        Long givenId = 1L;
        when(queryAdapter.doesCountryIdExists(any(Long.class))).thenReturn(Boolean.FALSE);
        assertThatThrownBy(() -> service.deleteCountryById(givenId))
                .isInstanceOf(BusinessVerificationException.class)
                .hasMessageContaining("there's not register");
    }

    @DisplayName("Successful country update should invoke update method on port")
    @Test
    void updateCountrySuccessShouldInvokeUpdateCountryPort() {
        Country givenCountry = getDefaultCountryWithId();
        UpdateCountryCommand givenCommand = getUpdateCommandFromCountry(givenCountry);
        when(countryMapper.toDomain(any(UpdateCountryCommand.class))).thenReturn(givenCountry);
        when(queryAdapter.doesCountryIdExists(any(Long.class))).thenReturn(Boolean.TRUE);
        when(estateManagerAdapter.updateCountry(any(Country.class))).thenReturn(getDefaultCountryWithId());
        Country updatedCountry = service.updateCountry(givenCommand);
        verify(countryMapper, atLeastOnce()).toDomain(any(UpdateCountryCommand.class));
        verify(estateManagerAdapter, atLeastOnce()).updateCountry(countryArgumentCaptor.capture());
        assertThat(countryArgumentCaptor.getValue()).isNotNull();
        assertThat(updatedCountry).isNotNull();
    }

    @DisplayName("Fail update country id that does not exists should throws BusinessVerificationException")
    @Test
    void updateCountryByIdThatDoesNotExistsShouldThrowsBusinessVerificationException() {
        Country givenCountry = getDefaultCountryWithId();
        UpdateCountryCommand givenCommand = getUpdateCommandFromCountry(givenCountry);
        when(queryAdapter.doesCountryIdExists(any(Long.class))).thenReturn(Boolean.FALSE);
        assertThatThrownBy(() -> service.updateCountry(givenCommand))
                .isInstanceOf(BusinessVerificationException.class)
                .hasMessageContaining("there's not register");
    }

    @DisplayName("Fail create country with invalid data should throws BusinessVerificationException")
    @MethodSource("provideRegistersForBusinessVerificationException")
    @ParameterizedTest
    void createCountryFailShouldThrowsBusinessVerificationException(Country argumentCountry, String expected) {
        Country givenCountry = getDefaultCountryWithoutId();
        CreateCountryCommand givenCommand = getCreateCommandFromCountry(argumentCountry);
        when(countryMapper.toDomain(any(CreateCountryCommand.class))).thenReturn(argumentCountry);
        when(queryAdapter.findAll()).thenReturn(Arrays.asList(givenCountry));
        assertThatThrownBy(() -> service.createCountry(givenCommand))
                .isInstanceOf(BusinessVerificationException.class)
                .hasMessageContaining(expected);
    }

    private UpdateCountryCommand getUpdateCommandFromCountry(Country country) {
        return new UpdateCountryCommand(
                country.getId().orElse(new Country.CountryId(1L)).getValue(),
                new CountryData(
                        country.getNumericCode(),
                        country.getAlpha3Code(),
                        country.getName()
                )
        );
    }

    private Country getDefaultCountryWithoutId() {
        return CountryTestData.defaultCountry().withId(null).build();
    }

    private Country getDefaultCountryWithId() {
        return CountryTestData.defaultCountry().build();
    }
}