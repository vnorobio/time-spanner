package dev.neytor.backend.timespanner.territory.country.adapter.out.persistence.jpa;

import dev.neytor.backend.timespanner.common.CountryTestData;
import dev.neytor.backend.timespanner.territory.country.adapter.out.persistence.jpa.entity.CountryEntity;
import dev.neytor.backend.timespanner.territory.country.adapter.out.persistence.jpa.entity.CountryMapper;
import dev.neytor.backend.timespanner.territory.country.domain.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Testing Country Query Adapter")
@ExtendWith(MockitoExtension.class)
class CountryQueryAdapterTest {

    @InjectMocks
    private CountryQueryAdapter queryAdapter;

    @Mock
    private CountryRepository repository;

    @Mock
    private CountryMapper mapper;

    @BeforeEach
    public void setup() {
        this.queryAdapter = new CountryQueryAdapter(repository, mapper);
    }

    @DisplayName("Success find country by Alpha3Code should return Optional<Country>")
    @Test
    void successFindCountryByAlpha3CodeShouldReturnOptionalCountry() {
        Country givenCountry = getCountryWithId();
        CountryEntity givenCountryEntity = getCountryEntity(givenCountry);
        when(mapper.toDomain(any(CountryEntity.class))).thenReturn(givenCountry);
        when(repository.findByAlpha3Code(any(String.class))).thenReturn(Optional.of(givenCountryEntity));
        Optional<Country> result = queryAdapter.findCountryByAlpha3Code("CountryAlpha3Code");
        verify(mapper, atLeastOnce()).toDomain(any(CountryEntity.class));
        verify(repository, atLeastOnce()).findByAlpha3Code(any(String.class));
        assertThat(result).isPresent();
    }

    @DisplayName("Fail find country by Alpha3Code should return Optional<empty>")
    @Test
    void FailFindCountryByAlpha3CodeShouldReturnEmptyOptional() {
        when(repository.findByAlpha3Code(any(String.class))).thenReturn(Optional.empty());
        Optional<Country> result = queryAdapter.findCountryByAlpha3Code("CountryAlpha3Code");
        verify(mapper, never()).toDomain(any());
        verify(repository, atLeastOnce()).findByAlpha3Code(any(String.class));
        assertThat(result).isNotPresent();
    }

    @DisplayName("Success find country by NumericCode should return Optional<Country>")
    @Test
    void successFindCountryByNumericCodeShouldReturnOptionalCountry() {
        Country givenCountry = getCountryWithId();
        CountryEntity givenCountryEntity = getCountryEntity(givenCountry);
        when(mapper.toDomain(any(CountryEntity.class))).thenReturn(givenCountry);
        when(repository.findByNumericCode(any(Integer.class))).thenReturn(Optional.of(givenCountryEntity));
        Optional<Country> result = queryAdapter.findByNumericCode(123);
        verify(mapper, atLeastOnce()).toDomain(any(CountryEntity.class));
        verify(repository, atLeastOnce()).findByNumericCode(any(Integer.class));
        assertThat(result).isPresent();
    }

    @DisplayName("Fail find country by NumericCode should return Optional<empty>")
    @Test
    void FailFindCountryByNumericCodeShouldReturnEmptyOptional() {
        when(repository.findByNumericCode(any(Integer.class))).thenReturn(Optional.empty());
        Optional<Country> result = queryAdapter.findByNumericCode(123);
        verify(mapper, never()).toDomain(any());
        verify(repository, atLeastOnce()).findByNumericCode(any(Integer.class));
        assertThat(result).isNotPresent();
    }

    @DisplayName("Success find country by Name should return Optional<Country>")
    @Test
    void successFindCountryByNameShouldReturnOptionalCountry() {
        Country givenCountry = getCountryWithId();
        CountryEntity givenCountryEntity = getCountryEntity(givenCountry);
        when(mapper.toDomain(any(CountryEntity.class))).thenReturn(givenCountry);
        when(repository.findByName(any(String.class))).thenReturn(Optional.of(givenCountryEntity));
        Optional<Country> result = queryAdapter.findByName("CountryName");
        verify(mapper, atLeastOnce()).toDomain(any(CountryEntity.class));
        verify(repository, atLeastOnce()).findByName(any(String.class));
        assertThat(result).isPresent();
    }

    @DisplayName("Fail find country by Name should return Optional<empty>")
    @Test
    void FailFindCountryByNameShouldReturnEmptyOptional() {
        when(repository.findByName(any(String.class))).thenReturn(Optional.empty());
        Optional<Country> result = queryAdapter.findByName("CountryName");
        verify(mapper, never()).toDomain(any());
        verify(repository, atLeastOnce()).findByName(any(String.class));
        assertThat(result).isNotPresent();
    }

    @DisplayName("Find all method of repository should be called")
    @Test
    void findAll() {
        Country givenCountry = getCountryWithId();
        List<CountryEntity> givenCountryEntities = Arrays.asList(getCountryEntity(givenCountry));
        when(repository.findAll()).thenReturn(givenCountryEntities);
        when(mapper.toDomain(any(CountryEntity.class))).thenReturn(givenCountry);
        List<Country> returnedList = queryAdapter.findAll();
        verify(repository, atLeastOnce()).findAll();
        verify(mapper, atLeastOnce()).toDomain(any(CountryEntity.class));
        assertThat(returnedList).isNotEmpty();
    }

    @DisplayName("Find by Name containing method of repository should be called")
    @Test
    void findByNameContaining() {
        Country givenCountry = getCountryWithId();
        List<CountryEntity> givenCountryEntities = Arrays.asList(getCountryEntity(givenCountry));
        when(repository.findByNameContaining(any(String.class))).thenReturn(givenCountryEntities);
        when(mapper.toDomain(any(CountryEntity.class))).thenReturn(givenCountry);
        List<Country> returnedList = queryAdapter.findByNameContaining("PartianName");
        verify(repository, atLeastOnce()).findByNameContaining(any(String.class));
        verify(mapper, atLeastOnce()).toDomain(any(CountryEntity.class));
        assertThat(returnedList).isNotEmpty();
    }

    @DisplayName("Exists by Id method of repository should be called")
    @Test
    void doesCountryIdExists() {
        Long givenId = 1L;
        when(repository.existsById(any(Long.class))).thenReturn(Boolean.TRUE);
        Boolean response = queryAdapter.doesCountryIdExists(givenId);
        verify(repository, atLeastOnce()).existsById(any(Long.class));
        assertThat(response).isTrue();
    }

    private Country getCountryWithId() {
        return CountryTestData.defaultCountry().build();
    }

    private CountryEntity getCountryEntity(Country country) {
        return new CountryEntity(
                country.getId().orElse(new Country.CountryId(1L)).getValue(),
                country.getAlpha3Code(),
                country.getName(),
                country.getNumericCode()
        );
    }
}