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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountryPersistenceAdapterTest {

    @InjectMocks
    private CountryPersistenceAdapter adapter;

    @Mock
    private CountryRepository repository;

    @Mock
    private CountryMapper mapper;

    @BeforeEach
    public void setup() {
        this.adapter = new CountryPersistenceAdapter(repository, mapper);
    }

    @DisplayName("Successful country creation should return saved country")
    @Test
    void createCountrySuccessShouldReturnSavedCountry() {
        Country givenCountry = getCountryWithoutId();
        CountryEntity givenCountryEntity = getCountryEntity(givenCountry);
        when(mapper.toJpaEntity(any(Country.class))).thenReturn(givenCountryEntity);
        when(mapper.toDomain(any(CountryEntity.class))).thenReturn(givenCountry);
        when(repository.save(any(CountryEntity.class))).thenReturn(givenCountryEntity);
        Country returnedCountry = adapter.createCountry(givenCountry);
        verify(mapper,atLeastOnce()).toJpaEntity(any(Country.class));
        verify(mapper,atLeastOnce()).toDomain(any(CountryEntity.class));
        verify(repository,atLeastOnce()).save(any(CountryEntity.class));
        assertThat(returnedCountry).isNotNull();
    }

    @DisplayName("Successful country creation should return saved country")
    @Test
    void updateCountry() {
        CountryEntity givenCountryEntity = getCountryEntity(getCountryWithId());
        Country givenCountry = getCountryWithId();
        when(mapper.toJpaEntity(any(Country.class))).thenReturn(givenCountryEntity);
        when(mapper.toDomain(any(CountryEntity.class))).thenReturn(givenCountry);
        when(repository.existsById(any(Long.class))).thenReturn(Boolean.TRUE);
        when(repository.save(any(CountryEntity.class))).thenReturn(givenCountryEntity);
        Country returnedCountry = adapter.updateCountry(givenCountry);
        verify(repository,atLeastOnce()).save(any(CountryEntity.class));
        verify(repository,atLeastOnce()).existsById(any(Long.class));
        assertThat(returnedCountry).isNotNull();
    }

    @DisplayName("Successful country deletion should return confirmation")
    @Test
    void deleteCountryById() {
        long givenId = 1L;
        doNothing().when(repository).deleteById(any(Long.class));
        when(repository.existsById(any(Long.class))).thenReturn(Boolean.FALSE);
        Boolean response = adapter.deleteCountryById(givenId);
        verify(repository,atLeastOnce()).deleteById(any(Long.class));
        verify(repository,atLeastOnce()).existsById(any(Long.class));
        assertThat(response).isTrue();
    }

    private Country getCountryWithId() {
        return CountryTestData.defaultCountry().build();
    }

    private Country getCountryWithoutId() {
        return CountryTestData.defaultCountry().withId(null).build();
    }

    private CountryEntity getCountryEntity(Country country){
        return new CountryEntity(
                country.getId().orElse(new Country.CountryId(1L)).getValue(),
                country.getAlpha3Code(),
                country.getName(),
                country.getNumericCode()
        );
    }


}