package dev.neytor.backend.timespanner.territory.country.application.service;

import dev.neytor.backend.timespanner.common.UseCase;
import dev.neytor.backend.timespanner.territory.country.application.port.in.CountryEstateManagerUseCase;
import dev.neytor.backend.timespanner.territory.country.application.port.in.command.CreateCountryCommand;
import dev.neytor.backend.timespanner.territory.country.application.port.in.command.DomainMapper;
import dev.neytor.backend.timespanner.territory.country.application.port.in.command.UpdateCountryCommand;
import dev.neytor.backend.timespanner.territory.country.application.port.out.CountryEstateManagerPort;
import dev.neytor.backend.timespanner.territory.country.domain.Country;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@UseCase
@Transactional
public class CountryEstateManagerService implements CountryEstateManagerUseCase {

    private final CountryEstateManagerPort estateManagerPort;

    @Override
    public Country createCountry(CreateCountryCommand createCommand) {
        Country country = DomainMapper.toDomain(createCommand);
        return estateManagerPort.createCountry(country);
    }

    @Override
    public Boolean deleteCountryById(Long id) {
        return estateManagerPort.deleteCountryById(id);
    }

    @Override
    public Country updateCountry(UpdateCountryCommand updateCommand) {
        Country country = DomainMapper.toDomain(updateCommand);
        return estateManagerPort.updateCountry(country);
    }
}
