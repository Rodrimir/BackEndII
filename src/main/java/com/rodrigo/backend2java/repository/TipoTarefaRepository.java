package com.rodrigo.backend2java.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.rodrigo.backend2java.model.TipoTarefa;

@Repository
public class TipoTarefaRepository {

    private final JdbcTemplate jdbc;

    public TipoTarefaRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<TipoTarefa> rowMapper = (rs, rowNum) -> {
        TipoTarefa t = new TipoTarefa();
        t.setId(rs.getLong("id"));
        t.setNome(rs.getString("nome"));
        return t;
    };

    public List<TipoTarefa> listarTodos() {
        return jdbc.query("SELECT * FROM tipos_tarefa", rowMapper);
    }

    public void salvar(TipoTarefa tipo) {
        jdbc.update("INSERT INTO tipos_tarefa (nome) VALUES (?)", tipo.getNome());
    }

    public void atualizar(TipoTarefa tipo) {
        jdbc.update("UPDATE tipos_tarefa SET nome = ? WHERE id = ?", tipo.getNome(), tipo.getId());
    }

    public void excluir(Long id) {
        jdbc.update("DELETE FROM tipos_tarefa WHERE id = ?", id);
    }
}
