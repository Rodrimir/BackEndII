package com.rodrigo.backend2java.service;

import com.rodrigo.backend2java.model.AvatarCatalogo;
import com.rodrigo.backend2java.model.Habito;
import com.rodrigo.backend2java.model.RegistroDiario;
import com.rodrigo.backend2java.model.Usuario;
import com.rodrigo.backend2java.model.dto.response.AvatarGaleriaDTO;
import com.rodrigo.backend2java.repository.AvatarCatalogoRepository;
import com.rodrigo.backend2java.repository.HabitoRepository;
import com.rodrigo.backend2java.repository.RegistroDiarioRepository;
import com.rodrigo.backend2java.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GamificacaoService {

    private final HabitoRepository habitoRepository;
    private final RegistroDiarioRepository registroRepository;
    private final UsuarioRepository usuarioRepository;
    private final AvatarCatalogoRepository avatarRepository;

    @Transactional 
    public void processarExecucaoDiaria(UUID habitoId) {
        LocalDate hoje = LocalDate.now();

        // @audit-info Evita que o usuário clique "Feito" duas vezes, eviando duplicação de registros e inconsistências de streaks/XP
        if (registroRepository.buscarPorHabitoEData(habitoId, hoje).isPresent()) {
            throw new RuntimeException("Hábito já marcado como feito hoje!");
        }

        Habito habito = habitoRepository.findById(habitoId)
                .orElseThrow(() -> new RuntimeException("Hábito não encontrado"));

        // @audit-info Cria o registro diário
        RegistroDiario registro = RegistroDiario.builder()
                .habitoId(habitoId)
                .dataExecucao(hoje)
                .horaConclusao(LocalDateTime.now())
                .build();
        registroRepository.save(registro);

        //  // @audit-info Atualiza Streak e XP do Hábito
        habito.setStreakAtual(habito.getStreakAtual() + 1);
        habito.setXpAcumulado(habito.getXpAcumulado() + 10); // +10 XP por execução mínima
        habitoRepository.save(habito);

        // // @audit-info Atualiza o XP Global do Usuário para evoluir o Avatar
        Usuario usuario = usuarioRepository.findById(habito.getUsuarioId()).orElseThrow();
        usuario.setXpTotal(usuario.getXpTotal() + 10);
        usuario.setStreakGlobal(usuario.getStreakGlobal() + 1);
        usuarioRepository.save(usuario);
    }

    public List<AvatarGaleriaDTO> gerarGaleriaDoUsuario(UUID usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        List<AvatarCatalogo> avatares = avatarRepository.listarTodosOrdenados();

        return avatares.stream().map(avatar -> {
            // @audit-info Gamificação:  bloqueado olhando pro XP do usuário
            boolean bloqueado = usuario.getXpTotal() < avatar.getNivelMinimoXp();
            
            return new AvatarGaleriaDTO(
                    avatar.getId(),
                    avatar.getNome(),
                    avatar.getNivelMinimoXp(),
                    avatar.getAssetVisualUrl(),
                    avatar.getOrdemEvolucao(),
                    bloqueado
            );
        }).collect(Collectors.toList());
    }

    @Transactional
    public void desfazerExecucaoDiaria(UUID habitoId) {
        LocalDate hoje = LocalDate.now();
        RegistroDiario registro = registroRepository.buscarPorHabitoEData(habitoId, hoje)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado para hoje"));
        
        registroRepository.delete(registro);

        Habito habito = habitoRepository.findById(habitoId).orElseThrow();
        habito.setStreakAtual(Math.max(0, habito.getStreakAtual() - 1));
        habito.setXpAcumulado(Math.max(0, habito.getXpAcumulado() - 10));
        habitoRepository.save(habito);

        Usuario usuario = usuarioRepository.findById(habito.getUsuarioId()).orElseThrow();
        usuario.setXpTotal(Math.max(0, usuario.getXpTotal() - 10));
        usuario.setStreakGlobal(Math.max(0, usuario.getStreakGlobal() - 1));
        usuarioRepository.save(usuario);
    }
}