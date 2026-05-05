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