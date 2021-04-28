package dev.neytor.backend.timespanner.territory.country.application.port.in.command;

import dev.neytor.backend.timespanner.territory.country.application.port.in.command.model.CountryCommandMapper;
import dev.neytor.backend.timespanner.territory.country.application.port.in.command.model.CountryData;
import dev.neytor.backend.timespanner.territory.country.domain.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Testing Country Command Mapper")
@ExtendWith(MockitoExtension.class)
class CountryCommandMapperTest {

    private CountryCommandMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new CountryCommandMapper();
    }

    @DisplayName("Success domain mapped should have same information as create command ")
    @Test
    void mapCreateCommandToDomain() {
        CreateCountryCommand creteCommand = new CreateCountryCommand(getCountryData());
        Country resultCountry =  mapper.toDomain(creteCommand);
        assertThat(resultCountry.getId()).isNotPresent();
        assertThat(resultCountry.getAlpha3Code()).isEqualTo(creteCommand.getData().getAlpha3Code());
        assertThat(resultCountry.getName()).isEqualTo(creteCommand.getData().getName());
        assertThat(resultCountry.getNumericCode()).isEqualTo(creteCommand.getData().getNumericCode());
    }



    @DisplayName("Success domain mapped should have same information as update command ")
    @Test
    void mapUpdateCommandToDomain() {
        UpdateCountryCommand updateCommand = new UpdateCountryCommand(
                1L,
                getCountryData()
        );
        Country resultCountry =  mapper.toDomain(updateCommand);
        assertThat(resultCountry.getId()).isPresent();
        assertThat(resultCountry.getId().get().getValue()).isEqualTo(updateCommand.getId());
        assertThat(resultCountry.getAlpha3Code()).isEqualTo(updateCommand.getData().getAlpha3Code());
        assertThat(resultCountry.getName()).isEqualTo(updateCommand.getData().getName());
        assertThat(resultCountry.getNumericCode()).isEqualTo(updateCommand.getData().getNumericCode());
    }

    private CountryData getCountryData() {
        return new CountryData(
                1234567890,
                "APM",
                "Wonderland"
        );
    }
}