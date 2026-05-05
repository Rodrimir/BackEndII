package com.rodrigo.backend2java.service;

import com.rodrigo.backend2java.model.Habito;
import com.rodrigo.backend2java.model.HabitoDiaSemana;
import com.rodrigo.backend2java.model.MicroHabito;
import com.rodrigo.backend2java.model.dto.request.NovoHabitoDTO;
import com.rodrigo.backend2java.model.dto.response.HabitoDetalhadoDTO;
import com.rodrigo.backend2java.repository.HabitoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HabitoService {

    private final HabitoRepository habitoRepository;

    public HabitoDetalhadoDTO criarHabito(UUID usuarioId, NovoHabitoDTO request) {
        Set<HabitoDiaSemana> dias = request.diasSemana().stream()
                .map(dia -> HabitoDiaSemana.builder().diaSemana(dia).build())
                .collect(Collectors.toSet());

        Set<MicroHabito> microHabitos = request.microHabitos().stream()
                .map(mh -> MicroHabito.builder()
                        .ordemFase(mh.ordemFase())
                        .metaPratica(mh.metaPratica())
                        .metaEmocional(mh.metaEmocional())
                        .build())
                .collect(Collectors.toSet());

        Habito habito = Habito.builder()
                .usuarioId(usuarioId)
                .nome(request.nome())
                .categoria(request.categoria())
                .streakAtual(0)
                .xpAcumulado(0)
                .ativo(true)
                .dataCriacao(LocalDateTime.now())
                .diasSemana(dias)
                .microHabitos(microHabitos)
                .build();

        Habito salvo = habitoRepository.save(habito);
        
        return buscarDetalhadoPorId(salvo.getId());
    }

    public List<HabitoDetalhadoDTO> listarPorUsuario(UUID usuarioId) {
        return habitoRepository.findAllByUsuarioId(usuarioId).stream()
                .map(h -> buscarDetalhadoPorId(h.getId()))
                .collect(Collectors.toList());
    }

    public HabitoDetalhadoDTO buscarDetalhadoPorId(UUID id) {
        Habito habito = habitoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hábito não encontrado"));

        Set<Integer> dias = habito.getDiasSemana().stream().map(HabitoDiaSemana::getDiaSemana).collect(Collectors.toSet());
        
        List<HabitoDetalhadoDTO.MicroHabitoDTO> micros = habito.getMicroHabitos().stream()
                .map(mh -> new HabitoDetalhadoDTO.MicroHabitoDTO(mh.getId(), mh.getOrdemFase(), mh.getMetaPratica(), mh.getMetaEmocional()))
                .collect(Collectors.toList());

        return new HabitoDetalhadoDTO(habito.getId(), habito.getNome(), habito.getCategoria(),
                habito.getStreakAtual(), habito.getXpAcumulado(), habito.getAtivo(),
                habito.getDataCriacao(), dias, micros);

    }


    // @audit-info update
    public HabitoDetalhadoDTO atualizarHabito(UUID id, NovoHabitoDTO request) {
        Habito habito = habitoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hábito não encontrado"));

        habito.setNome(request.nome());
        habito.setCategoria(request.categoria());

        habito.getDiasSemana().clear();
        request.diasSemana().forEach(dia -> 
            habito.getDiasSemana().add(HabitoDiaSemana.builder().diaSemana(dia).build())
        );

        habito.getMicroHabitos().clear();
        request.microHabitos().forEach(mh -> 
            habito.getMicroHabitos().add(MicroHabito.builder()
                    .ordemFase(mh.ordemFase())
                    .metaPratica(mh.metaPratica())
                    .metaEmocional(mh.metaEmocional())
                    .build())
        );

        Habito atualizado = habitoRepository.save(habito);
        return buscarDetalhadoPorId(atualizado.getId());
    }

    // @audit-info delete
    public void deletarHabito(UUID id) {
        if (!habitoRepository.existsById(id)) {
            throw new RuntimeException("Hábito não encontrado");
        }
        habitoRepository.deleteById(id);
    }
}