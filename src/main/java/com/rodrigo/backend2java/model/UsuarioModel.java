package com.rodrigo.backend2java.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("usuarios")
public class Usuario {
    
    @Id
    private Long id;
    
    private String nome;
    private String nickname;
}