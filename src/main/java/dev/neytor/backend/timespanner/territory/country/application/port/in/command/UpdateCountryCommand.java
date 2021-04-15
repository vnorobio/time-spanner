package dev.neytor.backend.timespanner.territory.country.application.port.in.command;

import dev.neytor.backend.timespanner.common.SelfValidating;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Value
@EqualsAndHashCode(callSuper = false)
public class UpdateCountryCommand extends SelfValidating<UpdateCountryCommand> {
    @NotNull
    private final Long id;
    @NotNull
    private final Integer numericCode;
    @NotEmpty
    private final String alpha3Code;
    @NotEmpty
    private final String name;
    @NotEmpty
    private final String divisionsDisplayName;
    @NotEmpty
    private final String subDivisionsDisplayName;

    public UpdateCountryCommand(Long id,
                                Integer numericCode,
                                String alpha3Code,
                                String name,
                                String divisionsDisplayName,
                                String subDivisionsDisplayName) {
        this.id = id;
        this.numericCode = numericCode;
        this.alpha3Code = alpha3Code;
        this.name = name;
        this.divisionsDisplayName = divisionsDisplayName;
        this.subDivisionsDisplayName = subDivisionsDisplayName;
        this.validateSelf();
    }
}
