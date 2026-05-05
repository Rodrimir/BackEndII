package com.rodrigo.backend2java.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rodrigo.backend2java.service.GamificacaoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/registros")
@RequiredArgsConstructor
public class RegistroController {

    private final GamificacaoService gamificacaoService;

    @PostMapping("/habito/{habitoId}/feito")
    public ResponseEntity<Void> marcarHabitoComoFeito(@PathVariable UUID habitoId) {
        gamificacaoService.processarExecucaoDiaria(habitoId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/habito/{habitoId}/desfazer")
    public ResponseEntity<Void> desfazerHabitoComoFeito(@PathVariable UUID habitoId) {
        gamificacaoService.desfazerExecucaoDiaria(habitoId);
        return ResponseEntity.noContent().build();
    }
}