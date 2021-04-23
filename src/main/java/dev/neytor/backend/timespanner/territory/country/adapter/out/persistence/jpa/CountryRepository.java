package dev.neytor.backend.timespanner.territory.country.adapter.out.persistence.jpa;

import dev.neytor.backend.timespanner.territory.country.adapter.out.persistence.jpa.entity.CountryEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<CountryEntity, Long> {

}
