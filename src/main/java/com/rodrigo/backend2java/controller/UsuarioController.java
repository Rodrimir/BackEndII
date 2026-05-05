package com.rodrigo.backend2java.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rodrigo.backend2java.model.dto.request.OnboardingRequestDTO;
import com.rodrigo.backend2java.model.dto.response.PerfilUsuarioDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    @GetMapping("/{id}")
    public ResponseEntity<PerfilUsuarioDTO> buscarPerfil(@PathVariable UUID id) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/onboarding")
    public ResponseEntity<Void> salvarOnboarding(
            @PathVariable UUID id, 
            @RequestBody OnboardingRequestDTO request) {
        return ResponseEntity.ok().build();
    }
}