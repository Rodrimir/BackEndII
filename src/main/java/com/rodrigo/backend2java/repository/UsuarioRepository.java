package com.rodrigo.backend2java.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.rodrigo.backend2java.model.Usuario;

public interface UsuarioRepository extends ListCrudRepository<Usuario, UUID> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
}