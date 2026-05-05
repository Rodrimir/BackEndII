package com.rodrigo.backend2java.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rodrigo.backend2java.model.dto.request.CadastroUsuarioDTO;
import com.rodrigo.backend2java.model.dto.request.OnboardingRequestDTO;
import com.rodrigo.backend2java.model.dto.response.PerfilUsuarioDTO;
import com.rodrigo.backend2java.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/busca/{id}")
    public ResponseEntity<PerfilUsuarioDTO> buscarPerfil(@PathVariable UUID id) {
        return ResponseEntity.ok(usuarioService.buscarPerfilCompleto(id));
    }

    @PostMapping("/salvar/onboarding/{id}")
    public ResponseEntity<Void> salvarOnboarding(
            @PathVariable UUID id, 
            @RequestBody OnboardingRequestDTO request) {
        usuarioService.registrarOnboarding(id, request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<PerfilUsuarioDTO> atualizarUsuario(
            @PathVariable UUID id,
            @RequestBody CadastroUsuarioDTO request) {
        return ResponseEntity.ok(usuarioService.atualizarUsuario(id, request));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable UUID id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/atualizar/onboarding/{id}")
    public ResponseEntity<Void> atualizarOnboarding(
            @PathVariable UUID id,
            @RequestBody OnboardingRequestDTO request) {
        usuarioService.atualizarOnboarding(id, request);
        return ResponseEntity.ok().build();
    }
}