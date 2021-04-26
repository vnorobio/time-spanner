package dev.neytor.backend.timespanner.territory.country.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.neytor.backend.timespanner.territory.country.adapter.in.web.dto.DtoDataMapper;
import dev.neytor.backend.timespanner.territory.country.application.port.in.command.CountryData;
import dev.neytor.backend.timespanner.territory.country.application.port.in.command.CreateCountryCommand;
import dev.neytor.backend.timespanner.territory.country.application.service.CountryEstateManagerService;
import dev.neytor.backend.timespanner.territory.country.application.service.CountryQueryService;
import dev.neytor.backend.timespanner.territory.country.domain.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CountryControllerTest {

    @InjectMocks
    private CountryController controller;

    @Mock
    private CountryEstateManagerService estateManagerService;

    @Mock
    private CountryQueryService queryService;

    @Mock
    private DtoDataMapper dtoMapper;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        this.controller = new CountryController(estateManagerService, queryService, dtoMapper);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void create() throws Exception {
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
}