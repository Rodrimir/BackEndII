import org.springframework.beans.factory.annotation.Autowired;
import com.fhi.stock_ws.model.response.LoginResponse;
import com.fhi.stock_ws.repository.LoginRepository;
import org.springframework.stereotype.Service;
package com.fhi.stock_ws.service;

@Service
public class LoginService {
    @Autowired
    private LoginRepository loginRepository;
    public LoginResponse login(int login, String senha) {
        return loginRepository.buscarUsuario(login, senha);
    }
}
