package com.rodrigo.backend2java.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/registros")
@RequiredArgsConstructor
public class RegistroController {
    @PostMapping("/habito/{habitoId}/feito")
    public ResponseEntity<Void> marcarHabitoComoFeito(@PathVariable UUID habitoId) {
        return ResponseEntity.ok().build();
    }
}