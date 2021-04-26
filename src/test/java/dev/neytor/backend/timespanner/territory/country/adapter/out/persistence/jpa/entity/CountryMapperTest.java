package dev.neytor.backend.timespanner.territory.country.adapter.out.persistence.jpa.entity;

import dev.neytor.backend.timespanner.common.CountryTestData;
import dev.neytor.backend.timespanner.territory.country.domain.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DisplayName("Testing Country Mapper")
@ExtendWith(MockitoExtension.class)
class CountryMapperTest {

    private CountryMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new CountryMapper();
    }

    @DisplayName("Success domain mapped should have same information as entity")
    @Test
    void toDomainSuccessCountryShouldHaveSameDataAsEntity() {
        CountryEntity givenEntity = getEntity();
        Country resultCountry = mapper.toDomain(givenEntity);
        assertThat(resultCountry.getId().get().getValue()).isEqualTo(givenEntity.getId());
        assertThat(resultCountry.getAlpha3Code()).isEqualTo(givenEntity.getAlpha3Code());
        assertThat(resultCountry.getName()).isEqualTo(givenEntity.getName());
        assertThat(resultCountry.getNumericCode()).isEqualTo(givenEntity.getNumericCode());
    }

    @DisplayName("Success entity mapped should have same information as domain")
    @Test
    void toJpaEntitySuccessEntityShouldHaveSameDaraAsDomain() {
        Country givenCountry = CountryTestData.defaultCountry().build();
        CountryEntity resultEntity = mapper.toJpaEntity(givenCountry);
        assertThat(resultEntity.getId()).isEqualTo(givenCountry.getId().get().getValue());
        assertThat(resultEntity.getAlpha3Code()).isEqualTo(givenCountry.getAlpha3Code());
        assertThat(resultEntity.getName()).isEqualTo(givenCountry.getName());
        assertThat(resultEntity.getNumericCode()).isEqualTo(givenCountry.getNumericCode());
    }

    private CountryEntity getEntity() {
        Country country = CountryTestData.defaultCountry().build();
        return new CountryEntity(
                country.getId().get().getValue(),
                country.getAlpha3Code(),
                country.getName(),
                country.getNumericCode()
        );
    }
}