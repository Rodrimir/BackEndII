package com.rodrigo.backend2java.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponse {

    private int id;
    private String senha;

    public static LoginResponse fromResultSet(ResultSet rs) throws SQLException {
        LoginResponse resp = new LoginResponse();
        resp.setId(rs.getInt("id"));
        resp.setSenha(rs.getString("senha"));
        return resp;
    }
}
