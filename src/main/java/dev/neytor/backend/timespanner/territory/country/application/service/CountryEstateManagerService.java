package dev.neytor.backend.timespanner.territory.country.application.service;

import dev.neytor.backend.timespanner.common.UseCase;
import dev.neytor.backend.timespanner.common.exception.BusinessVerificationException;
import dev.neytor.backend.timespanner.territory.country.application.port.in.CountryEstateManagerUseCase;
import dev.neytor.backend.timespanner.territory.country.application.port.in.command.CreateCountryCommand;
import dev.neytor.backend.timespanner.territory.country.application.port.in.command.CountryCommandMapper;
import dev.neytor.backend.timespanner.territory.country.application.port.in.command.UpdateCountryCommand;
import dev.neytor.backend.timespanner.territory.country.application.port.out.CountryEstateManagerPort;
import dev.neytor.backend.timespanner.territory.country.application.port.out.CountryQueryPort;
import dev.neytor.backend.timespanner.territory.country.domain.Country;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@UseCase
@Transactional
public class CountryEstateManagerService implements CountryEstateManagerUseCase {

    private final CountryEstateManagerPort estateManagerPort;
    private final CountryQueryPort queryPort;
    private final CountryCommandMapper countryMapper;

    @Override
    public Country createCountry(CreateCountryCommand createCommand) {
        Country country = countryMapper.toDomain(createCommand);
        validateCountryDataConstrains(country);
        return estateManagerPort.createCountry(country);
    }

    @Override
    public Boolean deleteCountryById(Long id) {
        validateThatCountryIdAlreadyExists(id);
        return estateManagerPort.deleteCountryById(id);
    }

    @Override
    public Country updateCountry(UpdateCountryCommand updateCommand) {
        validateThatCountryIdAlreadyExists(updateCommand.getId());
        Country country = countryMapper.toDomain(updateCommand);
        validateCountryDataConstrains(country);
        return estateManagerPort.updateCountry(country);
    }

    private void validateThatCountryIdAlreadyExists(Long id) {
        if (!doesCountryIdExists(id)) {
            String propertyName = "id";
            trowBusinessVerificationException(id.toString(), propertyName, NOT_EXIST_REGISTER_WITH_PROPERTY);
        }
    }

    private void trowBusinessVerificationException(String value, String propertyName, String message) {
        throw new BusinessVerificationException(
                String.format(message, propertyName, value)
        );
    }

    private void validateCountryDataConstrains(Country country) {
        List<Country> countries = queryPort.findAll();

        if (countries
                .stream()
                .anyMatch(given -> given.getName().equals(country.getName()))) {
            trowBusinessVerificationException(country.getName(), "name",ALREADY_EXIST_REGISTER_WITH_PROPERTY);
        }
        if (countries
                .stream()
                .anyMatch(given -> given.getNumericCode().equals(country.getNumericCode()))) {
            trowBusinessVerificationException(country.getNumericCode().toString(), "numeric code", ALREADY_EXIST_REGISTER_WITH_PROPERTY);
        }
        if (countries
                .stream()
                .anyMatch(given -> given.getAlpha3Code().equals(country.getAlpha3Code()))) {
            trowBusinessVerificationException(country.getAlpha3Code(), "alpha 3 code", ALREADY_EXIST_REGISTER_WITH_PROPERTY);
        }
    }

    private Boolean doesCountryIdExists(Long id){
        return queryPort.doesCountryIdExists(id);
    }

    private final String ALREADY_EXIST_REGISTER_WITH_PROPERTY = "there another register with %s: %s";
    private final String NOT_EXIST_REGISTER_WITH_PROPERTY = "there's not register with %s: %s";

}
