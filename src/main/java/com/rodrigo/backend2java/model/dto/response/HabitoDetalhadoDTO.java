package com.rodrigo.backend2java.model.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public record HabitoDetalhadoDTO(
    UUID id,
    String nome,
    String categoria,
    Integer streakAtual,
    Integer xpAcumulado,
    Boolean ativo,
    LocalDateTime dataCriacao,
    Set<Integer> diasSemana,
    List<MicroHabitoDTO> microHabitos
) {
    public record MicroHabitoDTO(
        UUID id,
        Integer ordemFase,
        String metaPratica,
        String metaEmocional
    ) {}
}