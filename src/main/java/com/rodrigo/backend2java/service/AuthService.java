package com.rodrigo.backend2java.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.rodrigo.backend2java.model.Usuario;
import com.rodrigo.backend2java.model.dto.request.CadastroUsuarioDTO;
import com.rodrigo.backend2java.model.dto.request.LoginRequestDTO;
import com.rodrigo.backend2java.model.dto.response.TokenResponseDTO;
import com.rodrigo.backend2java.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;

    public TokenResponseDTO autenticar(LoginRequestDTO request) {
        // @audit-info : Integrar Spring Security e JWT
        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        // @audit-info : Aqui colocamos token JWT simulado, mas em um cenário real, você geraria um token JWT válido aqui
        return new TokenResponseDTO("token-jwt-simulado-aqui", "Bearer");
    }

    public void cadastrar(CadastroUsuarioDTO request) {
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new RuntimeException("E-mail já está em uso");
        }

        Usuario novoUsuario = Usuario.builder()
                .nome(request.nome())
                .email(request.email())
                .senhaHash(request.senha())  // @audit-info : Aplicar BCryptPasswordEncoder
                .streakGlobal(0)
                .xpTotal(0)
                .dataCriacao(LocalDateTime.now())
                .build();

        usuarioRepository.save(novoUsuario);
    }
}