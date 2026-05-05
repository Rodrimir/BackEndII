package com.rodrigo.backend2java.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import com.rodrigo.backend2java.model.AvatarCatalogo;

public interface AvatarCatalogoRepository extends ListCrudRepository<AvatarCatalogo, UUID> {
    @Query("SELECT * FROM avatares_catalogo ORDER BY ordem_evolucao ASC")
    List<AvatarCatalogo> listarTodosOrdenados();
}