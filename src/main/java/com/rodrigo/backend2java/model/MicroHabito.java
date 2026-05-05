package com.rodrigo.backend2java.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table("micro_habitos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MicroHabito {

    @Id
    private UUID id;
    
    // Não precisamos mapear o ID do Habito aqui, o Spring JDBC faz isso pela classe pai
    
    @Column("ordem_fase")
    private Integer ordemFase;

    @Column("meta_pratica")
    private String metaPratica;

    @Column("meta_emocional")
    private String metaEmocional;
}