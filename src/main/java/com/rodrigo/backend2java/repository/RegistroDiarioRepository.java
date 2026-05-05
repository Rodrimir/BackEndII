package com.rodrigo.backend2java.repository;

import com.rodrigo.backend2java.model.RegistroDiario;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface RegistroDiarioRepository extends ListCrudRepository<RegistroDiario, UUID> {
    @Query("SELECT * FROM registros_diarios WHERE habito_id = :habitoId AND data_execucao = :dataExecucao")
    Optional<RegistroDiario> buscarPorHabitoEData(@Param("habitoId") UUID habitoId, @Param("dataExecucao") LocalDate dataExecucao);
}