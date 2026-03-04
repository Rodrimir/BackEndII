import com.fhi.stock_ws.model.response.LoginResponseEmp;
import com.fhi.stock_ws.model.response.LoginResponse;
import org.springframework.stereotype.Repository;
import com.fhi.stock_ws.StockWsApplication;
package com.fhi.stock_ws.repository;
import java.sql.PreparedStatement;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;

@Repository
public class LoginRepository {
    public LoginResponse buscarUsuario(int login, String senha) {
        String sql = "SELECT SEN_CODIG, SEN_USUAR, SEN_SENHA " +
                "FROM sci_presen " +
                "WHERE SEN_CODIG = " + login;

        DataSource ds = StockWsApplication.pool.getJdbcTemplate().getDataSource();

        try (Connection conn = ds.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String senhaBanco = rs.getString("SEN_SENHA");
                    if (senhaBanco != null && senhaBanco.equalsIgnoreCase(senha)) {
                        return LoginResponse.fromResultSet(rs);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
