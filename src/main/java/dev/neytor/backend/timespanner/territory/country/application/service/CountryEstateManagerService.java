package dev.neytor.backend.timespanner.territory.country.application.service;

import dev.neytor.backend.timespanner.common.UseCase;
import dev.neytor.backend.timespanner.territory.country.application.port.in.CountryEstateManagerUseCase;
import dev.neytor.backend.timespanner.territory.country.application.port.in.command.CreateCountryCommand;
import dev.neytor.backend.timespanner.territory.country.application.port.in.command.CountryCommandMapper;
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
    private final CountryCommandMapper countryMapper;

    @Override
    public Country createCountry(CreateCountryCommand createCommand) {
        return estateManagerPort.createCountry(countryMapper.toDomain(createCommand));
    }

    @Override
    public Boolean deleteCountryById(Long id) {
        estateManagerPort.deleteCountryById(id);
        return !existCountryWithId(id);
    }

    @Override
    public Country updateCountry(UpdateCountryCommand updateCommand) {
        return estateManagerPort.updateCountry(countryMapper.toDomain(updateCommand));
    }

    Boolean existCountryWithId(Long id){
        return estateManagerPort.existCountryWithId(id);
    }

}
