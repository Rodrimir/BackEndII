package com.rodrigo.backend2java.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table("habitos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Habito {

    @Id
    private UUID id;

    @Column("usuario_id")
    private UUID usuarioId;

    private String nome;
    
    private String categoria;

    @Column("streak_atual")
    private Integer streakAtual;

    @Column("xp_acumulado")
    private Integer xpAcumulado;
    
    private Boolean ativo;

    @Column("data_criacao")
    private LocalDateTime dataCriacao;

    @MappedCollection(idColumn = "habito_id")
    @Builder.Default
    private Set<HabitoDiaSemana> diasSemana = new HashSet<>();

    @MappedCollection(idColumn = "habito_id")
    @Builder.Default
    private Set<MicroHabito> microHabitos = new HashSet<>();
}