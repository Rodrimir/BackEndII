package com.rodrigo.backend2java.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Table("registros_diarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroDiario {

    @Id
    private UUID id;

    @Column("habito_id")
    private UUID habitoId;

    @Column("data_execucao")
    private LocalDate dataExecucao;

    @Column("hora_conclusao")
    private LocalDateTime horaConclusao;

    @Column("sentimento_pos_conclusao")
    private String sentimentoPosConclusao;
}