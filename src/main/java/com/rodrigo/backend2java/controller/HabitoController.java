package com.rodrigo.backend2java.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rodrigo.backend2java.model.dto.request.NovoHabitoDTO;
import com.rodrigo.backend2java.model.dto.response.HabitoDetalhadoDTO;
import com.rodrigo.backend2java.service.HabitoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/habitos")
@RequiredArgsConstructor
public class HabitoController {

    private final HabitoService habitoService;

    @PostMapping("/criar/{usuarioId}")
    public ResponseEntity<HabitoDetalhadoDTO> criarHabito(
            @PathVariable UUID usuarioId, 
            @Valid @RequestBody NovoHabitoDTO request) {
        
        HabitoDetalhadoDTO novoHabito = habitoService.criarHabito(usuarioId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoHabito);
    }

    @GetMapping("/listar/{usuarioId}")
    public ResponseEntity<List<HabitoDetalhadoDTO>> listarHabitosDoUsuario(@PathVariable UUID usuarioId) {
        
        List<HabitoDetalhadoDTO> habitos = habitoService.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(habitos);
    }

    @GetMapping("/detalhar/{id}")
    public ResponseEntity<HabitoDetalhadoDTO> detalharHabito(@PathVariable UUID id) {
        
        HabitoDetalhadoDTO habito = habitoService.buscarDetalhadoPorId(id);
        return ResponseEntity.ok(habito);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<HabitoDetalhadoDTO> atualizarHabito(
            @PathVariable UUID id, 
            @Valid @RequestBody NovoHabitoDTO request) {
            
        HabitoDetalhadoDTO habitoAtualizado = habitoService.atualizarHabito(id, request);
        return ResponseEntity.ok(habitoAtualizado);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarHabito(@PathVariable UUID id) {
        
        habitoService.deletarHabito(id);
        return ResponseEntity.noContent().build();
    }
}