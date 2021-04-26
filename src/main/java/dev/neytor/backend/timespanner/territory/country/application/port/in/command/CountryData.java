package dev.neytor.backend.timespanner.territory.country.application.port.in.command;

import dev.neytor.backend.timespanner.common.SelfValidating;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder(toBuilder = true)
public class CountryData extends SelfValidating<CreateCountryCommand> {
    @NotNull
    private Integer numericCode;
    @NotEmpty
    private String alpha3Code;
    @NotEmpty
    private String name;

    public CountryData(Integer numericCode, String alpha3Code, String name) {
        this.numericCode = numericCode;
        this.alpha3Code = alpha3Code;
        this.name = name;
        this.validateSelf();
    }
}
