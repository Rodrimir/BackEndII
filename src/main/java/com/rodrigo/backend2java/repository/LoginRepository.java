package com.rodrigo.backend2java.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rodrigo.backend2java.model.LoginResponse;

@Repository
public class LoginRepository {

    @Autowired
    private DataSource dataSource;

    public LoginResponse buscarUsuario(int id, String senha) {
        String sql = "SELECT id, senha FROM usuarios WHERE id = ?";

        try (Connection pool = dataSource.getConnection(); PreparedStatement stmt = pool.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String senhaBanco = rs.getString("senha");
                    if (senhaBanco != null && senhaBanco.equalsIgnoreCase(senha)) {
                        return LoginResponse.fromResultSet(rs);
                    }
                }
            }
        } catch (java.sql.SQLException e) {
            System.out.println("Erro no repo: " + e.getMessage());
        }
        return null;
    }
}
// @Repository
// public class LoginRepository {

//     public LoginResponse buscarUsuario(int login, String senha) {
//         if (login == 123 && "admin".equals(senha)) {
//             LoginResponse mockUser = new LoginResponse();
//             mockUser.setSenCodig(123);
//             mockUser.setSenSenha("admin");  
//             return mockUser;
//         }
//         return null; 
//     }
// }
