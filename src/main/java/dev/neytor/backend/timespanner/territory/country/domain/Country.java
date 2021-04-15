package dev.neytor.backend.timespanner.territory.country.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Country {

    @Getter
    private final CountryId id;

    @Getter
    private final Integer numericCode;

    @Getter
    private final String alphaCode3;

    @Getter
    private final String name;

    @Getter
    private final String divisionsDisplayName;

    @Getter
    private final String subDivisionsDisplayName;

    public static Country withoutId(
            Integer numericCode,
            String alphaCode3,
            String name,
            String divisionsDisplayName,
            String subDivisionsDisplayName
    ) {
        return new Country(
                null,
                numericCode,
                alphaCode3,
                name,
                divisionsDisplayName,
                subDivisionsDisplayName
        );
    }

    public static Country withId(
            CountryId id,
            Integer numericCode,
            String alphaCode3,
            String name,
            String divisionsDisplayName,
            String subDivisionsDisplayName
    ) {
        return new Country(
                id,
                numericCode,
                alphaCode3,
                name,
                divisionsDisplayName,
                subDivisionsDisplayName
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
