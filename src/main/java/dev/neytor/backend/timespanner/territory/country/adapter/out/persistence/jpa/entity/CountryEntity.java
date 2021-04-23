package dev.neytor.backend.timespanner.territory.country.adapter.out.persistence.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "country")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String alpha3Code;

    @Column
    private String name;

    @Column
    private Integer numericCode;
}
