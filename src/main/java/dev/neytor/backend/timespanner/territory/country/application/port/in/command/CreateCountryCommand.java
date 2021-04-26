package dev.neytor.backend.timespanner.territory.country.application.port.in.command;

import dev.neytor.backend.timespanner.common.SelfValidating;
import lombok.EqualsAndHashCode;
import lombok.Value;
import javax.validation.constraints.NotNull;

@Value
@EqualsAndHashCode(callSuper = false)
public class CreateCountryCommand extends SelfValidating<CreateCountryCommand> {
    @NotNull
    private final CountryData data;

    public CreateCountryCommand(CountryData data) {
        this.data = data;
        this.validateSelf();
    }
}
