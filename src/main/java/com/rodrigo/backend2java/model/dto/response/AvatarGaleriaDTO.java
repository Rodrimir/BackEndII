package com.rodrigo.backend2java.model.dto.response;

import java.util.UUID;

public record AvatarGaleriaDTO(
    UUID id,
    String nome,
    Integer nivelMinimoXp,
    String assetVisualUrl,
    Integer ordemEvolucao,
    Boolean bloqueado
) {}