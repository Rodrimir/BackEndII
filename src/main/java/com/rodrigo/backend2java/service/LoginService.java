package com.rodrigo.backend2java.service;
import org.springframework.beans.factory.annotation.Autowired;
import com.rodrigo.backend2java.repository.LoginRepository;
import com.rodrigo.backend2java.model.LoginResponse;
import org.springframework.stereotype.Service;
@Service
public class LoginService {
    @Autowired
    private LoginRepository loginRepository;
    public LoginResponse login(int login, String senha) {
        return loginRepository.buscarUsuario(login, senha);
    }
}