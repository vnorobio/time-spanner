package dev.neytor.backend.timespanner.territory.country.application.port.in.command;

import dev.neytor.backend.timespanner.common.SelfValidating;
import dev.neytor.backend.timespanner.territory.country.application.port.in.command.model.CountryData;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@EqualsAndHashCode(callSuper = false)
public class UpdateCountryCommand extends SelfValidating<UpdateCountryCommand> {
    @NotNull
    private final Long id;
    @NotNull
    private final CountryData data;

    public UpdateCountryCommand(Long id,
                                CountryData data) {
        this.id = id;
        this.data = data;
        this.validateSelf();
    }
}
