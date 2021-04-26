package dev.neytor.backend.timespanner.territory.country.adapter.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
public class CountryDto {
    @Positive
    @NotNull
    private Long id;
    @NotEmpty
    private String alpha3Code;
    @NotEmpty
    private String name;
    @Positive
    @NotNull
    private Integer numericCode;
}
