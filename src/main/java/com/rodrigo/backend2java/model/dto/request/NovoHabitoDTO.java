package com.rodrigo.backend2java.model.dto.request;

import java.util.List;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record NovoHabitoDTO(
    @NotBlank(message = "O nome do hábito é obrigatório")
    String nome,
    
    String categoria,
    
    @NotEmpty(message = "É necessário definir pelo menos um dia na semana")
    Set<Integer> diasSemana,
    
    List<MicroHabitoRequestDTO> microHabitos
) {
    // Record aninhado para estruturar a requisição dos micro-hábitos
    public record MicroHabitoRequestDTO(
        Integer ordemFase,
        @NotBlank(message = "A meta prática não pode ser vazia")
        String metaPratica,
        String metaEmocional
    ) {}
}