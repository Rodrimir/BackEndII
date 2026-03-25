package com.rodrigo.backend2java.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.rodrigo.backend2java.model.Rotina;

@Repository
public class RotinaRepository {

    private final JdbcTemplate jdbc;

    public RotinaRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<Rotina> rowMapper = (rs, rowNum) -> {
        Rotina r = new Rotina();
        r.setId(rs.getLong("id"));
        r.setUsuarioId(rs.getLong("usuario_id"));
        r.setNome(rs.getString("nome"));
        r.setDescricao(rs.getString("descricao"));
        return r;
    };

    public List<Rotina> listarTodos() {
        return jdbc.query("SELECT * FROM rotinas", rowMapper);
    }

    public void salvar(Rotina rotina) {
        String sql = "INSERT INTO rotinas (usuario_id, nome, descricao) VALUES (?, ?, ?)";
        jdbc.update(sql, rotina.getUsuarioId(), rotina.getNome(), rotina.getDescricao());
    }

    public void atualizar(Rotina rotina) {
        String sql = "UPDATE rotinas SET usuario_id = ?, nome = ?, descricao = ? WHERE id = ?";
        jdbc.update(sql, rotina.getUsuarioId(), rotina.getNome(), rotina.getDescricao(), rotina.getId());
    }

    public void excluir(Long id) {
        jdbc.update("DELETE FROM rotinas WHERE id = ?", id);
    }
}
