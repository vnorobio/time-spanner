package dev.neytor.backend.timespanner.territory.country.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.neytor.backend.timespanner.common.CountryTestData;
import dev.neytor.backend.timespanner.territory.country.adapter.in.web.dto.CountryDto;
import dev.neytor.backend.timespanner.territory.country.adapter.in.web.dto.DtoDataFormatter;
import dev.neytor.backend.timespanner.territory.country.application.port.in.command.CountryData;
import dev.neytor.backend.timespanner.territory.country.application.port.in.command.CreateCountryCommand;
import dev.neytor.backend.timespanner.territory.country.application.service.CountryEstateManagerService;
import dev.neytor.backend.timespanner.territory.country.application.service.CountryQueryService;
import dev.neytor.backend.timespanner.territory.country.domain.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Testing Country Controller")
@ExtendWith(MockitoExtension.class)
class CountryControllerTest {

    @InjectMocks
    private CountryController controller;

    @Mock
    private CountryEstateManagerService estateManagerService;

    @Mock
    private CountryQueryService queryService;

    @Mock
    private DtoDataFormatter dtoMapper;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        this.controller = new CountryController(estateManagerService, queryService, dtoMapper);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @DisplayName("Success create should return status OK")
    @Test
    void createSuccessShouldReturnStatusOK() throws Exception {
        CountryData requestData = new CountryData(
                123,
                "CountryAlpha3Code",
                "CountryName"
        );
        Country country = Country.withId(
                new Country.CountryId(1L),
                requestData.getNumericCode(),
                requestData.getAlpha3Code(),
                requestData.getName()
        );
        String jsonData = objectMapper.writeValueAsString(requestData);
        BDDMockito.given(estateManagerService.createCountry(any(CreateCountryCommand.class))).willReturn(country);
        mockMvc.perform(post("/api/v1/countries")
                .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                .content(jsonData))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    private CreateCountryCommand toCreateCommand(CountryData data) {
        return new CreateCountryCommand(data);
    }

    @DisplayName("Success find all should return dto list")
    @Test
    void findAllSuccessShouldReturnDtoList() throws Exception {
        List<Country> givenCountries = Arrays.asList(CountryTestData.defaultCountry().build());
        BDDMockito.given(queryService.findAll()).willReturn(givenCountries);
        mockMvc.perform(get("/api/v1/countries"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$",hasSize(1)));
    }
}