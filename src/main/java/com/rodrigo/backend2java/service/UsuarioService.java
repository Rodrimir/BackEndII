package com.rodrigo.backend2java.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rodrigo.backend2java.model.UsuarioModel;
import com.rodrigo.backend2java.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<UsuarioModel> listarTodos() {
        return repository.findAll();
    }

    public UsuarioModel buscarPorId(Integer id) {
        return repository.findById(id);
    }

    public UsuarioModel salvar(UsuarioModel usuario) {
        return repository.save(usuario);
    }

    public void deletar(Integer id) {
        repository.deleteById(id);
    }
}
