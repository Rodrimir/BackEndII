package com.rodrigo.backend2java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.rodrigo.backend2java.model.AuthRequest;
import com.rodrigo.backend2java.model.LoginResponse;
import com.rodrigo.backend2java.service.LoginService;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public String exibirTelaLogin(Model model) {
        model.addAttribute("authRequest", new AuthRequest());
        System.out.println("Exibindo tela de login");
        return "login";
    }

    @PostMapping("/login")
    public String processarLogin(@ModelAttribute AuthRequest authRequest, Model model) {
        LoginResponse user = loginService.login(authRequest.getId(), authRequest.getSenha());
        if (user != null) {
            model.addAttribute("mensagem", "Sucesso no login");
            System.out.println("Login bem-sucedido para usuário ID: " + authRequest.getId());
            return "login";
        } else {
            model.addAttribute("erro", "Erro de login");
        }
        return "login";
    }
}
