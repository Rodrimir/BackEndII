package com.rodrigo.backend2java.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rodrigo.backend2java.model.dto.request.NovoHabitoDTO;
import com.rodrigo.backend2java.model.dto.response.HabitoDetalhadoDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/habitos")
@RequiredArgsConstructor
public class HabitoController {

    @PostMapping("/usuario/{usuarioId}")
    public ResponseEntity<HabitoDetalhadoDTO> criarHabito(
            @PathVariable UUID usuarioId, 
            @Valid @RequestBody NovoHabitoDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<HabitoDetalhadoDTO>> listarHabitosDoUsuario(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HabitoDetalhadoDTO> detalharHabito(@PathVariable UUID id) {
        return ResponseEntity.ok().build();
    }
}