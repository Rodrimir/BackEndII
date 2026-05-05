package com.rodrigo.backend2java.service;

import com.rodrigo.backend2java.repository.NotificacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificacaoService {
    // @audit-info Stub preparado para o envio de notificações de lembrete e cartões de cobrança futuros.
    private final NotificacaoRepository notificacaoRepository;
}