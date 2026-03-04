package com.fhi.stock_ws.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponse {

    private int senCodig;
    private String senUsuar;
    private String senSenha;

    public static LoginResponse fromResultSet(ResultSet rs) throws SQLException {
        LoginResponse resp = new LoginResponse();
        resp.setSenCodig(rs.getInt("SEN_CODIG"));
        resp.setSenUsuar(rs.getString("SEN_USUAR"));
        resp.setSenSenha(rs.getString("SEN_SENHA"));
        return resp;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "senCodig=" + senCodig +
                ", senUsuar='" + senUsuar + '\'' +
                ", senSenha='" + senSenha + '\'' +
                '}';
    }
}
