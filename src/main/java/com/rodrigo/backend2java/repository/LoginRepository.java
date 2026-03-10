package com.rodrigo.backend2java.repository;
import org.springframework.beans.factory.annotation.Autowired;
import com.rodrigo.backend2java.model.LoginResponse;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;

// @Repository
// public class LoginRepository {
//     @Autowired
//     private DataSource dataSource;
//     public LoginResponse buscarUsuario(int login, String senha) {
//         String sql = "SELECT SEN_CODIG, SEN_USUAR, SEN_SENHA FROM sci_presen WHERE SEN_CODIG = ?";
//         try (Connection conn = dataSource.getConnection();
//              PreparedStatement stmt = conn.prepareStatement(sql)) {
//             stmt.setInt(1, login);
//             try (ResultSet rs = stmt.executeQuery()) {
//                 if (rs.next()) {
//                     String senhaBanco = rs.getString("SEN_SENHA");
//                     if (senhaBanco != null && senhaBanco.equalsIgnoreCase(senha)) {
//                         return LoginResponse.fromResultSet(rs);
//                     }
//                 }
//             }
//         }
//         return null;
//     }
// }
@Repository
public class LoginRepository {

    public LoginResponse buscarUsuario(int login, String senha) {
        if (login == 123 && "admin".equals(senha)) {
            LoginResponse mockUser = new LoginResponse();
            mockUser.setSenCodig(123);
            mockUser.setSenSenha("admin");  
            return mockUser;
        }
        return null; 
    }
}