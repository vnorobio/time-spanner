package dev.neytor.backend.timespanner.territory.country.application.port.in.command;

import dev.neytor.backend.timespanner.common.SelfValidating;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Value
@EqualsAndHashCode(callSuper = false)
@Builder(toBuilder = true)
public class CountryData extends SelfValidating<CreateCountryCommand> {
    @NotNull
    Integer numericCode;
    @NotEmpty
    String alpha3Code;
    @NotEmpty
    String name;

    public CountryData(Integer numericCode, String alpha3Code, String name) {
        this.numericCode = numericCode;
        this.alpha3Code = alpha3Code;
        this.name = name;
        this.validateSelf();
    }
}
