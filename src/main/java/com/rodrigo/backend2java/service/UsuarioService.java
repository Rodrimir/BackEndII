package com.rodrigo.backend2java.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.rodrigo.backend2java.model.PerfilOnboarding;
import com.rodrigo.backend2java.model.Usuario;
import com.rodrigo.backend2java.model.dto.request.CadastroUsuarioDTO;
import com.rodrigo.backend2java.model.dto.request.OnboardingRequestDTO;
import com.rodrigo.backend2java.model.dto.response.PerfilUsuarioDTO;
import com.rodrigo.backend2java.repository.PerfilOnboardingRepository;
import com.rodrigo.backend2java.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PerfilOnboardingRepository onboardingRepository;

    public PerfilUsuarioDTO buscarPerfilCompleto(UUID id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        PerfilOnboarding onboarding = onboardingRepository.findByUsuarioId(id).orElse(null);

        PerfilUsuarioDTO.OnboardingInfoDTO onboardingDto = onboarding != null ?
                new PerfilUsuarioDTO.OnboardingInfoDTO(onboarding.getPrincipaisAtritos(), onboarding.getRegraInegociavelGeral()) : null;

        return new PerfilUsuarioDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getStreakGlobal(),
                usuario.getXpTotal(),
                onboardingDto
        );
    }

    public void registrarOnboarding(UUID usuarioId, OnboardingRequestDTO request) {
        PerfilOnboarding perfil = PerfilOnboarding.builder()
                .usuarioId(usuarioId)
                .principaisAtritos(request.principaisAtritos())
                .regraInegociavelGeral(request.regraInegociavelGeral())
                .dataResposta(LocalDateTime.now())
                .build();
        
        onboardingRepository.save(perfil);
    }

    public PerfilUsuarioDTO atualizarUsuario(UUID id, CadastroUsuarioDTO request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        usuario.setNome(request.nome());
        
        if (request.senha() != null && !request.senha().isBlank()) {
            usuario.setSenhaHash(request.senha());
        }
        
        usuarioRepository.save(usuario);
        return buscarPerfilCompleto(id);
    }

    public void deletarUsuario(UUID id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    public void atualizarOnboarding(UUID usuarioId, OnboardingRequestDTO request) {
        PerfilOnboarding perfil = onboardingRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Onboarding não encontrado"));
        
        perfil.setPrincipaisAtritos(request.principaisAtritos());
        perfil.setRegraInegociavelGeral(request.regraInegociavelGeral());
        
        onboardingRepository.save(perfil);
    }
}