package com.rodrigo.backend2java.repository;

import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.rodrigo.backend2java.model.MicroHabito;


public interface MicroHabitoRepository extends ListCrudRepository<MicroHabito, UUID> {
}