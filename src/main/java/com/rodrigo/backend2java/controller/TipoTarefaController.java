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

import com.rodrigo.backend2java.model.TipoTarefa;
import com.rodrigo.backend2java.repository.TipoTarefaRepository;

@RestController
@RequestMapping("/api/tipos-tarefa")
public class TipoTarefaController {

    private final TipoTarefaRepository repository;

    public TipoTarefaController(TipoTarefaRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/listar")
    public List<TipoTarefa> listar() {
        return repository.listarTodos();
    }

    @PostMapping("/salvar")
    public String salvar(@RequestBody TipoTarefa tipo) {
        repository.salvar(tipo);
        return "sucesso!";
    }

    @PutMapping("/atualizar")
    public String atualizar(@RequestParam Long id, @RequestBody TipoTarefa tipo) {
        tipo.setId(id);
        repository.atualizar(tipo);
        return "sucesso!";
    }

    @DeleteMapping("/excluir")
    public String excluir(@RequestParam Long id) {
        repository.excluir(id);
        return "sucesso!";
    }
}
