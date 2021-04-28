package dev.neytor.backend.timespanner.territory.country.adapter.in.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.neytor.backend.timespanner.common.CountryTestData;
import dev.neytor.backend.timespanner.common.ResponseHandler;
import dev.neytor.backend.timespanner.territory.country.adapter.in.web.dto.CountryDto;
import dev.neytor.backend.timespanner.territory.country.adapter.in.web.dto.DtoDataFormatter;
import dev.neytor.backend.timespanner.territory.country.application.port.in.command.UpdateCountryCommand;
import dev.neytor.backend.timespanner.territory.country.application.port.in.command.model.CountryData;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
    private DtoDataFormatter formatter;

    @Mock
    private ResponseHandler responseHandler;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        this.controller = new CountryController(estateManagerService, queryService, formatter, responseHandler);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @DisplayName("Success create should return status OK")
    @Test
    void createSuccessShouldReturnStatusCreated() throws Exception {
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
        BDDMockito.given(formatter.toDto(any(Country.class))).willReturn(getCountryDto(country));
        BDDMockito.given(estateManagerService.createCountry(any(CreateCountryCommand.class))).willReturn(country);
        BDDMockito.given(responseHandler
                .generateResponse(any(HttpStatus.class),any(Optional.class)))
                .willReturn(getResponse(country));
        mockMvc.perform(post("/api/v1/countries")
                .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                .content(jsonData))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @DisplayName("Success find all should return status Ok")
    @Test
    void findAllSuccessShouldReturnStatusOk() throws Exception {
        List<Country> givenCountries = Arrays.asList(CountryTestData.defaultCountry().build());
        BDDMockito.given(queryService.findAll()).willReturn(givenCountries);
        BDDMockito.given(responseHandler
                .generateResponse(any(HttpStatus.class),any(Optional.class)))
                .willReturn(getResponse(givenCountries));
        mockMvc.perform(get("/api/v1/countries"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @DisplayName("Success update all should return status Ok")
    @Test
    void updateShouldReturnStatusOk() throws Exception {
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
        BDDMockito.given(formatter.toDto(any(Country.class))).willReturn(getCountryDto(country));
        BDDMockito.given(estateManagerService.updateCountry(any(UpdateCountryCommand.class))).willReturn(country);
        BDDMockito.given(responseHandler
                .generateResponse(any(HttpStatus.class),any(Optional.class)))
                .willReturn(getResponse(country));
        mockMvc.perform(put("/api/v1/countries/{id}", Long.parseLong("1"))
                .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                .content(jsonData))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("Success delete all should return status accepted")
    @Test
    void deleteShouldReturnStatusOk() throws Exception {
        BDDMockito.given(estateManagerService.deleteCountryById(any(Long.class))).willReturn(Boolean.TRUE);
        BDDMockito.given(responseHandler
                .generateResponse(any(HttpStatus.class),any(Optional.class)))
                .willReturn(getResponse("Was deleted: " + Boolean.TRUE));
        mockMvc.perform(delete("/api/v1/countries/{id}", Long.parseLong("1")))
                .andExpect(status().is2xxSuccessful());
    }


    private CountryDto getCountryDto(Country country) {
        return new CountryDto(
                country.getId().get().getValue(),
                country.getAlpha3Code(),
                country.getName(),
                country.getNumericCode()
        );
    }

    private ResponseEntity<Object> getResponse(Object object) {
        Map<String, Object> map = new HashMap<>();
        map.put("content", object);
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

}