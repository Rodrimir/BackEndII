package com.rodrigo.backend2java.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rodrigo.backend2java.model.dto.response.AvatarGaleriaDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/avatares")
@RequiredArgsConstructor
public class AvatarController {

    @GetMapping("/galeria/{usuarioId}")
    public ResponseEntity<List<AvatarGaleriaDTO>> carregarGaleria(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok().build();
    }
}