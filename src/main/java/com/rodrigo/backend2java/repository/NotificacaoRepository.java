package com.rodrigo.backend2java.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.rodrigo.backend2java.model.Notificacao;

public interface NotificacaoRepository extends ListCrudRepository<Notificacao, UUID> {
    List<Notificacao> findAllByUsuarioIdAndLidaFalse(UUID usuarioId);
}