package com.rodrigo.backend2java.model.dto.response;

import java.util.UUID;

public record PerfilUsuarioDTO(
    UUID id,
    String nome,
    String email,
    Integer streakGlobal,
    Integer xpTotal,
    OnboardingInfoDTO onboarding
) {
    public record OnboardingInfoDTO(
        String principaisAtritos,
        String regraInegociavelGeral
    ) {}
}