package com.rodrigo.backend2java.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;
@Data
@Table("usuarios")
public class UsuarioModel {

    @Id
    private Integer id;

    private String nome;
    private String nickname;
}
