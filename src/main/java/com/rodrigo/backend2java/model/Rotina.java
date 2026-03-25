package com.rodrigo.backend2java.model;

import lombok.Data;

@Data
public class Rotina {

    private Long id;
    private Long usuarioId;
    private String nome;
    private String descricao;
}
