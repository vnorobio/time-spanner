package dev.neytor.backend.timespanner.territory.country.adapter.in.web;

import dev.neytor.backend.timespanner.territory.country.adapter.in.web.dto.CountryDto;
import dev.neytor.backend.timespanner.territory.country.adapter.in.web.dto.DtoDataFormatter;
import dev.neytor.backend.timespanner.territory.country.application.port.in.command.CountryData;
import dev.neytor.backend.timespanner.territory.country.application.port.in.command.CreateCountryCommand;
import dev.neytor.backend.timespanner.territory.country.application.service.CountryEstateManagerService;
import dev.neytor.backend.timespanner.territory.country.application.service.CountryQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CountryController {

    private final CountryEstateManagerService estateManagerService;

    private final CountryQueryService queryService;

    private final DtoDataFormatter formatter;

    @GetMapping("/countries")
    public ResponseEntity<List<CountryDto>> findAll(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(queryService.findAll().stream().map(formatter::toDto).collect(Collectors.toList()));
    }

    @PostMapping("/countries")
    public ResponseEntity<CountryDto> create(@RequestBody CountryData data){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(formatter.toDto(estateManagerService.createCountry(new CreateCountryCommand(data))));
    }
}
