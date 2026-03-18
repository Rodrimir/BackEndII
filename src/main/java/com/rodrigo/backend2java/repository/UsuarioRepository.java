package com.rodrigo.backend2java.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.rodrigo.backend2java.model.UsuarioModel;

@Repository
public class UsuarioRepository {

    private final JdbcTemplate jdbcTemplate;

    public UsuarioRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<UsuarioModel> mapeador = (rs, rowNum) -> {
        UsuarioModel u = new UsuarioModel();
        u.setId(rs.getInt("id"));
        u.setNome(rs.getString("nome"));
        u.setNickname(rs.getString("nickname"));
        return u;
    };

    public List<UsuarioModel> findAll() {
        System.out.println("Buscando todos os usuários");
        String sql = "SELECT * FROM usuarios ORDER BY id ASC";
        return jdbcTemplate.query(sql, mapeador);
    }

    public UsuarioModel findById(Integer id) {
        System.out.println("Buscando usuário com ID: " + id);
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        List<UsuarioModel> resultados = jdbcTemplate.query(sql, mapeador, id);

        if (resultados.isEmpty()) {
            return null;
        }
        return resultados.get(0);
    }

    public UsuarioModel save(UsuarioModel usuario) {
        System.out.println("Salvando usuário: " + usuario.getNome() + " com nickname: " + usuario.getNickname());
        if (usuario.getId() == null) {
            String sql = "INSERT INTO usuarios (nome, nickname, senha) VALUES (?, ?, '12345')";
            jdbcTemplate.update(sql, usuario.getNome(), usuario.getNickname());

        } else {
            String sql = "UPDATE usuarios SET nome = ?, nickname = ? WHERE id = ?";
            jdbcTemplate.update(sql, usuario.getNome(), usuario.getNickname(), usuario.getId());
        }
        return usuario;
    }

    public void deleteById(Integer id) {
        System.out.println("Deletando usuário com ID: " + id);
        String sql = "DELETE FROM usuarios WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
