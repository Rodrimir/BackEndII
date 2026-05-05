package com.rodrigo.backend2java.model.dto.response;

public record TokenResponseDTO(
    String token,
    String tipo
) {}