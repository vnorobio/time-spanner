package dev.neytor.backend.timespanner.territory.country.application.port.in.command;

import dev.neytor.backend.timespanner.common.SelfValidating;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CreateCountryCommand extends SelfValidating<CreateCountryCommand> {
    @NotNull
    private CountryData data;

    public CreateCountryCommand(CountryData data) {
        this.data = data;
        this.validateSelf();
    }
}
