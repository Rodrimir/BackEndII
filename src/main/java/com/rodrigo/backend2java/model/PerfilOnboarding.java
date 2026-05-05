package com.rodrigo.backend2java.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table("perfil_onboarding")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerfilOnboarding {

    @Id
    private UUID id;

    @Column("usuario_id")
    private UUID usuarioId;

    @Column("principais_atritos")
    private String principaisAtritos;

    @Column("regra_inegociavel_geral")
    private String regraInegociavelGeral;

    @Column("data_resposta")
    private LocalDateTime dataResposta;
}