package com.rodrigo.backend2java.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import com.rodrigo.backend2java.service.LoginService;
import com.rodrigo.backend2java.model.LoginResponse;
import com.rodrigo.backend2java.model.AuthRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;
    @GetMapping("/login")
    public String exibirTelaLogin(Model model) {
        model.addAttribute("authRequest", new AuthRequest());
        return "login"; 
    }
    @PostMapping("/login")
    public String processarLogin(@ModelAttribute AuthRequest authRequest, Model model) { 
        LoginResponse user = loginService.login(authRequest.getId(), authRequest.getSenha());
        if (user != null) {
            model.addAttribute(user.getSenUsuar());
            return "login"; 
        } else {
            model.addAttribute("erro!");
            return "login"; 
        }
    }
}