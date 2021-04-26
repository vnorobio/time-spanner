package dev.neytor.backend.timespanner.territory.country.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.Value;

import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Country {

    @Getter
    private final CountryId id;

    @Getter
    private final Integer numericCode;

    @Getter
    private final String alpha3Code;

    @Getter
    private final String name;

    public static Country withoutId(
            Integer numericCode,
            String alphaCode3,
            String name
    ) {
        return new Country(
                null,
                numericCode,
                alphaCode3,
                name
        );
    }

    public static Country withId(
            CountryId id,
            Integer numericCode,
            String alphaCode3,
            String name
    ) {
        return new Country(
                id,
                numericCode,
                alphaCode3,
                name
        );
    }

    public Optional<CountryId> getId() {
        return Optional.ofNullable(this.id);
    }

    @Value
    public static class CountryId {
        private Long value;
    }
}
