package com.rodrigo.backend2java.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.rodrigo.backend2java.model.Usuario;

@Repository
public class UsuarioRepository {

    private final JdbcTemplate jdbc;

    public UsuarioRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<Usuario> rowMapper = (rs, rowNum) -> {
        Usuario u = new Usuario();
        u.setId(rs.getLong("id"));
        u.setNome(rs.getString("nome"));
        u.setNickname(rs.getString("nickname"));
        return u;
    };

    public List<Usuario> listarTodos() {
        return jdbc.query("SELECT * FROM usuarios", rowMapper);
    }

    public void salvar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nome, nickname) VALUES (?, ?)";
        jdbc.update(sql, usuario.getNome(), usuario.getNickname());
    }

    public void atualizar(Usuario usuario) {
        String sql = "UPDATE usuarios SET nome = ?, nickname = ? WHERE id = ?";
        jdbc.update(sql, usuario.getNome(), usuario.getNickname(), usuario.getId());
    }

    public void excluir(Long id) {
        jdbc.update("DELETE FROM usuarios WHERE id = ?", id);
    }
}
