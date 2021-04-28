package dev.neytor.backend.timespanner.territory.country.adapter.in.web;

import dev.neytor.backend.timespanner.common.ResponseHandler;
import dev.neytor.backend.timespanner.territory.country.adapter.in.web.dto.DtoDataFormatter;
import dev.neytor.backend.timespanner.territory.country.application.port.in.command.model.CountryData;
import dev.neytor.backend.timespanner.territory.country.application.port.in.command.CreateCountryCommand;
import dev.neytor.backend.timespanner.territory.country.application.port.in.command.UpdateCountryCommand;
import dev.neytor.backend.timespanner.territory.country.application.service.CountryEstateManagerService;
import dev.neytor.backend.timespanner.territory.country.application.service.CountryQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CountryController {

    private final CountryEstateManagerService estateManagerService;

    private final CountryQueryService queryService;

    private final DtoDataFormatter formatter;

    private final ResponseHandler responseHandler;

    @GetMapping("/countries")
    public ResponseEntity<Object> findAll(){
        return responseHandler.generateResponse(
                HttpStatus.OK,
                Optional.of(queryService.findAll().stream().map(formatter::toDto).collect(Collectors.toList()))
        );
    }

    @PostMapping("/countries")
    public ResponseEntity<Object> create(@RequestBody CountryData data){
        return responseHandler.generateResponse(
                HttpStatus.CREATED,
                Optional.of(formatter.toDto(estateManagerService.createCountry(new CreateCountryCommand(data))))
        );
    }

    @PutMapping("/countries/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody CountryData data){
        return responseHandler.generateResponse(
                HttpStatus.OK,
                Optional.of(formatter.toDto(estateManagerService.updateCountry(new UpdateCountryCommand(id,data))))
        );
    }

    @DeleteMapping("/countries/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){
        return  responseHandler.generateResponse(
                HttpStatus.ACCEPTED,
                Optional.of("Was deleted: ".concat(estateManagerService.deleteCountryById(id).toString()))
        );
    }
}
