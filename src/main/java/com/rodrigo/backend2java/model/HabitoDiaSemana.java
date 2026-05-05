package com.rodrigo.backend2java.model;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table("habito_dias_semana")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HabitoDiaSemana {

    @Column("dia_semana")
    private Integer diaSemana;
    
}