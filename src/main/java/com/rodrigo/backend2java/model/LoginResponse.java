package com.rodrigo.backend2java.model;
import lombok.NoArgsConstructor;
import java.sql.SQLException;
import java.sql.ResultSet;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class LoginResponse {
    private int senCodig;
    private String senSenha;
    public static LoginResponse fromResultSet(ResultSet rs) throws SQLException {
        LoginResponse resp = new LoginResponse();
        resp.setSenCodig(rs.getInt("SEN_CODIG"));
        resp.setSenSenha(rs.getString("SEN_SENHA"));
        return resp;
    }
}