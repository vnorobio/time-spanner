package dev.neytor.backend.timespanner.common;

import dev.neytor.backend.timespanner.territory.country.domain.Country;

import java.util.Arrays;
import java.util.List;

public class CountryTestData {
    private static final Long ID_1 = 1L;
    private static final Long ID_2 = 2L;

    public static final List<Country> getCountries() {
        return Arrays.asList(defaultCountry().build(),
                defaultCountry().withId(new Country.CountryId(ID_2)).build());
    }

    public static CountryBuilder defaultCountry() {
        return new CountryBuilder()
                .withId(new Country.CountryId(ID_1))
                .withNumericCode(1234567890)
                .withAlpha3Code("APM")
                .withName("Wonderland")
                .withDivisionsDisplayName("aEstate")
                .withSubDivisionsDisplayName("aCity");
    }

    public static class CountryBuilder {
        private Country.CountryId id;
        private Integer numericCode;
        private String alpha3Code;
        private String name;
        private String divisionsDisplayName;
        private String subDivisionsDisplayName;

        public CountryBuilder withId(Country.CountryId id) {
            this.id = id;
            return this;
        }

        public CountryBuilder withNumericCode(Integer numericCode) {
            this.numericCode = numericCode;
            return this;
        }

        public CountryBuilder withAlpha3Code(String alpha3Code) {
            this.alpha3Code = alpha3Code;
            return this;
        }

        public CountryBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public CountryBuilder withDivisionsDisplayName(String divisionsDisplayName) {
            this.divisionsDisplayName = divisionsDisplayName;
            return this;
        }

        public CountryBuilder withSubDivisionsDisplayName(String subDivisionsDisplayName) {
            this.subDivisionsDisplayName = subDivisionsDisplayName;
            return this;
        }

        public Country build() {
            return Country.withId(
                    this.id,
                    this.numericCode,
                    this.alpha3Code,
                    this.name,
                    this.divisionsDisplayName,
                    this.subDivisionsDisplayName
            );
        }

    }
}
