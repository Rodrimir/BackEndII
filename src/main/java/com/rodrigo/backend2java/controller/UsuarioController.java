package com.rodrigo.backend2java.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rodrigo.backend2java.model.UsuarioModel;
import com.rodrigo.backend2java.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", service.listarTodos());
        return "usuarios/lista";
    }

    @GetMapping("/novo")
    public String novoUsuarioForm(Model model) {
        model.addAttribute("usuario", new UsuarioModel());
        return "usuarios/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editarUsuarioForm(@PathVariable Integer id, Model model) {
        UsuarioModel usuario = service.buscarPorId(id);
        if (usuario == null) {
            return "redirect:/usuarios";
        }
        model.addAttribute("usuario", usuario);
        return "usuarios/formulario";
    }

    @PostMapping("/salvar")
    public String salvarUsuario(@ModelAttribute("usuario") UsuarioModel usuario) {
        service.salvar(usuario);
        return "redirect:/usuarios";
    }

    @GetMapping("/deletar/{id}")
    public String deletarUsuario(@PathVariable Integer id) {
        service.deletar(id);
        return "redirect:/usuarios";
    }
}
