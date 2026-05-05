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

@Table("notificacoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notificacao {

    @Id
    private UUID id;

    @Column("usuario_id")
    private UUID usuarioId;

    private String tipo;

    private String mensagem;

    @Column("data_hora_envio")
    private LocalDateTime dataHoraEnvio;

    private Boolean lida;
}