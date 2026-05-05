-- 1. TABELA DE USUÁRIOS
-- Atende a: Página de Login, Cadastro e Perfil.
-- 3FN: Apenas dados dependentes exclusivamente do ID do usuário.
CREATE TABLE usuarios (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    senha_hash VARCHAR(255) NOT NULL,
    streak_global INT DEFAULT 0, -- Dias consecutivos de uso geral do app
    xp_total INT DEFAULT 0,      -- Somatório para evolução global do avatar
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. TABELA DE PERFILAMENTO / ONBOARDING (Questionário TDAH)
-- Atende a: Perfilamento Inicial (Frictionless).
-- 1:1 com Usuários. Separado para manter a tabela de usuários limpa (2FN/3FN).
CREATE TABLE perfil_onboarding (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    usuario_id UUID UNIQUE NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    principais_atritos TEXT,          -- Ex: "Esquecimento", "Paralisia de decisão"
    regra_inegociavel_geral TEXT,     -- A base da execução mínima
    data_resposta TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. TABELA DE HÁBITOS (Hábito Ativo)
-- Atende a: Página Inicial e Detalhes do Hábito.
-- Representa a relação N:1 (Vários hábitos podem pertencer a um usuário).
CREATE TABLE habitos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    usuario_id UUID NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    nome VARCHAR(255) NOT NULL,       -- Ex: "Beber mais água"
    categoria VARCHAR(100),           -- Ex: "Saúde", "Estudos"
    streak_atual INT DEFAULT 0,
    xp_acumulado INT DEFAULT 0,
    ativo BOOLEAN DEFAULT TRUE,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 4. TABELA DE CRONOGRAMA DO HÁBITO (Dias da Semana)
-- 1FN: Elimina atributos multivalorados (arrays de dias da semana dentro da tabela hábitos).
CREATE TABLE habito_dias_semana (
    habito_id UUID NOT NULL REFERENCES habitos(id) ON DELETE CASCADE,
    dia_semana INT NOT NULL CHECK (dia_semana BETWEEN 1 AND 7), -- 1=Domingo, 7=Sábado
    PRIMARY KEY (habito_id, dia_semana)
);

-- 5. TABELA DE MICRO-HÁBITOS
-- Atende a: Detalhes do Hábito / Aba de Micro-hábitos.
-- N:1 em relação a Hábitos. O detalhamento da execução mínima.
CREATE TABLE micro_habitos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    habito_id UUID NOT NULL REFERENCES habitos(id) ON DELETE CASCADE,
    ordem_fase INT NOT NULL,          -- Para sequenciamento
    meta_pratica TEXT NOT NULL,       -- Ex: "Ler apenas 1 página" (Reduz carga cognitiva)
    meta_emocional TEXT               -- Ex: "Focar no sentimento de realização"
);

-- 6. TABELA DE REGISTROS DIÁRIOS (O Botão "Feito")
-- Atende a: O botão rápido de "Feito" na Home. Feedback visual imediato.
CREATE TABLE registros_diarios (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    habito_id UUID NOT NULL REFERENCES habitos(id) ON DELETE CASCADE,
    data_execucao DATE NOT NULL,      -- Data em que foi feito
    hora_conclusao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    sentimento_pos_conclusao TEXT,    -- Opcional, para reforço psicológico
    -- Restrição para evitar duplicidade de "check" no mesmo hábito no mesmo dia
    UNIQUE(habito_id, data_execucao) 
);

-- 7. TABELA CATÁLOGO DE AVATARES (Gamificação)
-- Atende a: Mecânica de evolução visual e Galeria de Avatares.
-- 3FN: O catálogo é independente do usuário. O app calcula em tempo real via XP.
CREATE TABLE avatares_catalogo (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(100) NOT NULL,
    nivel_minimo_xp INT NOT NULL,     -- XP ou streak necessário para desbloquear
    asset_visual_url VARCHAR(255) NOT NULL, -- Caminho da imagem no storage
    ordem_evolucao INT UNIQUE NOT NULL
);

-- 8. TABELA DE NOTIFICAÇÕES
CREATE TABLE notificacoes (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    usuario_id UUID NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    tipo VARCHAR(50) NOT NULL,        -- Ex: "Lembrete", "Cartao_Enfrentamento"
    mensagem TEXT NOT NULL,
    data_hora_envio TIMESTAMP NOT NULL,
    lida BOOLEAN DEFAULT FALSE
);


Dados para testes:

-- 1. AVATARES DO CATÁLOGO (Inserir primeiro, pois não dependem de ninguém)
INSERT INTO avatares_catalogo (id, nome, nivel_minimo_xp, asset_visual_url, ordem_evolucao) VALUES
('d0eebc99-9c0b-4ef8-bb6d-6bb9bd380a41', 'Semente Focada', 0, '/assets/avatares/lvl1_semente.png', 1),
('d0eebc99-9c0b-4ef8-bb6d-6bb9bd380a42', 'Broto Consistente', 100, '/assets/avatares/lvl2_broto.png', 2),
('d0eebc99-9c0b-4ef8-bb6d-6bb9bd380a43', 'Árvore da Disciplina', 500, '/assets/avatares/lvl3_arvore.png', 3);

-- 2. USUÁRIOS
INSERT INTO usuarios (id, nome, email, senha_hash, streak_global, xp_total) VALUES
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'Eduardo Teixeira', 'eduardo@tempoclaro.com', '$2a$10$xyz123fakehash', 5, 120),
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'Juliana Gil', 'juliana@tempoclaro.com', '$2a$10$abc987fakehash', 12, 350);

-- 3. PERFIL ONBOARDING
INSERT INTO perfil_onboarding (usuario_id, principais_atritos, regra_inegociavel_geral) VALUES
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'Paralisia de decisão ao iniciar o TCC', 'Sempre abrir a IDE e o notion antes de pensar no que fazer.'),
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'Esquecimento de horários', 'Deixar a roupa do treino separada um dia antes.');

-- 4. HÁBITOS
INSERT INTO habitos (id, usuario_id, nome, categoria, streak_atual, xp_acumulado, ativo) VALUES
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a21', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'Desenvolver App Tempo Claro', 'Estudos', 3, 50, TRUE),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a22', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'Treino na Skyfit', 'Saúde', 5, 70, TRUE),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a23', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'Jogar Black Desert', 'Lazer', 12, 100, TRUE);

-- 5. CRONOGRAMA DO HÁBITO (Dias da Semana: 1=Domingo, 7=Sábado)
-- TCC: Segunda a Sexta (2, 3, 4, 5, 6)
INSERT INTO habito_dias_semana (habito_id, dia_semana) VALUES
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a21', 2), ('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a21', 3), 
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a21', 4), ('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a21', 5), 
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a21', 6),
-- Treino: Seg, Qua, Sex (2, 4, 6)
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a22', 2), ('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a22', 4), 
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a22', 6);

-- 6. MICRO-HÁBITOS
INSERT INTO micro_habitos (habito_id, ordem_fase, meta_pratica, meta_emocional) VALUES
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a21', 1, 'Ligar o PC e abrir o projeto', 'Focar apenas em ver a tela inicial'),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a21', 2, 'Escrever 1 linha de código ou documentação', 'Sentimento de destravamento'),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a22', 1, 'Tomar pré-treino / preparar a garrafa d''água', 'Despertar o corpo'),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a22', 2, 'Chegar na academia', 'O mais difícil já foi feito, agora é só executar');

-- 7. REGISTROS DIÁRIOS (Testando histórico: Ontem e Hoje)
INSERT INTO registros_diarios (habito_id, data_execucao, sentimento_pos_conclusao) VALUES
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a21', CURRENT_DATE - INTERVAL '1 day', 'Rendeu mais do que eu esperava'),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a22', CURRENT_DATE, 'Treino pesado, missão cumprida');

-- 8. NOTIFICAÇÕES
INSERT INTO notificacoes (usuario_id, tipo, mensagem, data_hora_envio, lida) VALUES
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'Lembrete', 'Sua regra inegociável te espera: abra a IDE!', CURRENT_TIMESTAMP, FALSE),
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'Recompensa', 'Streak de 12 dias atingido! Parabéns!', CURRENT_TIMESTAMP, TRUE);