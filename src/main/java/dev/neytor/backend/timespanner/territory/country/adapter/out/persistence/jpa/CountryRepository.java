package dev.neytor.backend.timespanner.territory.country.adapter.out.persistence.jpa;

import dev.neytor.backend.timespanner.territory.country.adapter.out.persistence.jpa.entity.CountryEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<CountryEntity, Long> {

    Optional<CountryEntity> findByAlpha3Code();

    Optional<CountryEntity> findByNumericCode();

    Optional<CountryEntity> findByName();

    List<CountryEntity> findByNameContaining(String name);
}
