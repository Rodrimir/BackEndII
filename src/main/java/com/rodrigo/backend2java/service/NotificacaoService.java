package com.rodrigo.backend2java.service;

import java.util.UUID;

import com.rodrigo.backend2java.repository.NotificacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificacaoService {
    
    private final NotificacaoRepository notificacaoRepository;

    public void marcarComoLida(UUID id) {
        com.rodrigo.backend2java.model.Notificacao notificacao = notificacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificação não encontrada"));
        notificacao.setLida(true);
        notificacaoRepository.save(notificacao);
    }

    public void deletarNotificacao(UUID id) {
        if (!notificacaoRepository.existsById(id)) {
            throw new RuntimeException("Notificação não encontrada");
        }
        notificacaoRepository.deleteById(id);
    }
}