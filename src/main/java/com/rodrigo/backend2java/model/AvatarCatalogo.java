package com.rodrigo.backend2java.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table("avatares_catalogo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvatarCatalogo {

    @Id
    private UUID id;
    
    private String nome;

    @Column("nivel_minimo_xp")
    private Integer nivelMinimoXp;

    @Column("asset_visual_url")
    private String assetVisualUrl;

    @Column("ordem_evolucao")
    private Integer ordemEvolucao;
}