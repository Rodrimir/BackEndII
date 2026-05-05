package com.rodrigo.backend2java.repository;

import com.rodrigo.backend2java.model.Habito;
import org.springframework.data.repository.ListCrudRepository;
import java.util.List;
import java.util.UUID;

public interface HabitoRepository extends ListCrudRepository<Habito, UUID> {
    List<Habito> findAllByUsuarioId(UUID usuarioId);
}