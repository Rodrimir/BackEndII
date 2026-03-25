package com.rodrigo.backend2java.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rodrigo.backend2java.model.Rotina;
import com.rodrigo.backend2java.repository.RotinaRepository;

@RestController
@RequestMapping("/api/rotinas")
public class RotinaController {

    private final RotinaRepository repository;

    public RotinaController(RotinaRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/listar")
    public List<Rotina> listar() {
        return repository.listarTodos();
    }

    @PostMapping("/salvar")
    public String salvar(@RequestBody Rotina rotina) {
        repository.salvar(rotina);
        return "sucesso!";
    }

    @PutMapping("/atualizar")
    public String atualizar(@RequestParam Long id, @RequestBody Rotina rotina) {
        rotina.setId(id);
        repository.atualizar(rotina);
        return "sucesso!";
    }

    @DeleteMapping("/excluir")
    public String excluir(@RequestParam Long id) {
        repository.excluir(id);
        return "sucesso!";
    }
}
