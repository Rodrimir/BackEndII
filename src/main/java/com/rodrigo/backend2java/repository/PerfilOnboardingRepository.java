package com.rodrigo.backend2java.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.rodrigo.backend2java.model.PerfilOnboarding;

public interface PerfilOnboardingRepository extends ListCrudRepository<PerfilOnboarding, UUID> {
    Optional<PerfilOnboarding> findByUsuarioId(UUID usuarioId);
}