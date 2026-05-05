## Projeto Tempo Claro

**Base paral localização de detalhes no projeto usando EXTENSION Inline Bookmarks, para ver notas no projeto com tag** @audit 

**ref prof:**
github: https://github.com/gillgonzales/cstsi_dbe2_202601_php_laravel

## **Banco de dados** atualizado para atender ao TCC

## DDL:
-- Extensão para geração de UUIDs no PostgreSQL
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

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

## Como Usar Docker Compose

### Pré-requisitos

- Docker instalado
- Docker Compose instalado
- Arquivo `.env` na raiz do projeto com as variáveis:
  ```env
  APP_PORT=8083
  DB_NAME=seu_banco
  DB_USER=seu_usuario
  DB_PASS=sua_senha
  FORWARD_DB_PORT=3306
  FORWARD_MYADMIN_PORT=8093
  ```

### Iniciar os Containers

```bash
docker compose down -v
docker compose up --build -d
docker compose up

docker compose up -d
```

### Parar os Containers

```bash
docker compose down
```

### Parar e Remover Volumes

```bash
docker compose down -v
```

### Visualizar Logs

```bash
# Todos os serviços
docker compose logs -f

# Serviço específico
docker compose logs -f app_php
docker compose logs -f mariadb
```

### Executar Comandos no Container

```bash
# Acessar shell do PHP
docker compose exec app_php bash

# Executar comando específico
docker compose exec app_php php -v

# Kill docker services

docker compose down -v
```



---

## Estrutura do Projeto

BackEndII
└── src/
    ├── main/
    │   ├── java/com/rodrigo/backend2java/   # Raiz da aplicação
    │   │   ├── TempoClaroApplication.java # Ponto de partida (Spring Boot Main)
    │   │   │
    │   │   ├── config/                    # Configurações gerais do projeto
    │   │   │   ├── CorsConfig.java        # Regras para permitir que o Flutter acesse a API
    │   │   │   └── SecurityConfig.java    # Configurações de autenticação e rotas públicas/privadas
    │   │   │
    │   │   ├── controller/                # Endpoints (URLs) que o Flutter vai chamar
    │   │   │   ├── AuthController.java    # Rotas de Login e Cadastro
    │   │   │   ├── UsuarioController.java # Rotas para Editar Perfil e Onboarding
    │   │   │   ├── HabitoController.java  # Rotas para criar/listar Hábitos e Micro-hábitos
    │   │   │   ├── RegistroController.java# Rota para o botão rápido de "Feito"
    │   │   │   └── AvatarController.java  # Rotas para listar a galeria e evolução gráfica
    │   │   │
    │   │   ├── exception/                 # Tratamento global de erros para evitar tela branca no Flutter
    │   │   │   ├── GlobalExceptionHandler.java # Captura e formata erros em JSON (ex: Erro 404, 400)
    │   │   │   └── RecursoNaoEncontradoException.java
    │   │   │
    │   │   ├── model/                     # Suas tabelas do banco de dados (Entidades JPA)
    │   │   │   ├── Usuario.java
    │   │   │   ├── PerfilOnboarding.java
    │   │   │   ├── Habito.java            # Aqui dentro mapeamos os "dias da semana"
    │   │   │   ├── MicroHabito.java
    │   │   │   ├── RegistroDiario.java
    │   │   │   ├── AvatarCatalogo.java
    │   │   │   ├── Notificacao.java
        │   │   ├── Notificacao.java
    │   │   │   └── dto/                       # Objetos de tráfego de dados (JSON)
    │   │   │       ├── request/               # O que o Flutter ENVIA para a API
    │   │   │       │   ├── LoginRequestDTO.java
    │   │   │       │   ├── CadastroUsuarioDTO.java
    │   │   │       │   ├── OnboardingRequestDTO.java
    │   │   │       │   └── NovoHabitoDTO.java
    │   │   │       └── response/              # O que a API DEVOLVE para o Flutter
    │   │   │           ├── TokenResponseDTO.java
    │   │   │           ├── PerfilUsuarioDTO.java
    │   │   │           ├── HabitoDetalhadoDTO.java
    │   │   │           └── AvatarGaleriaDTO.java
    │   │   │
    │   │   ├── repository/                # Interfaces que conversam com o PostgreSQL
    │   │   │   ├── UsuarioRepository.java
    │   │   │   ├── PerfilOnboardingRepository.java
    │   │   │   ├── HabitoRepository.java
    │   │   │   ├── MicroHabitoRepository.java
    │   │   │   ├── RegistroDiarioRepository.java
    │   │   │   ├── AvatarCatalogoRepository.java
    │   │   │   └── NotificacaoRepository.java
    │   │   │
    │   │   └── service/                   # Onde vive a inteligência, regras de negócio e cálculo de XP
    │   │       ├── AuthService.java       # Lógica de validação de senha e geração de token
    │   │       ├── UsuarioService.java    # Lógica de criação de conta e salvamento de onboarding
    │   │       ├── HabitoService.java     # Lógica de criação de hábitos baseados em regras inegociáveis
    │   │       ├── GamificacaoService.java# Lógica que processa o botão "Feito", calcula Streak e XP
    │   │       └── NotificacaoService.java
    │   │
    │   └── resources/
    │       ├── application.properties     # Credenciais do banco Postgres e configurações do servidor
    │       └── data.sql                   # Arquivo opcional para inserir os Avatares no banco na primeira execução

## Explicação Rápida dos Ajustes:
Remoção de templates e static: Como será uma API REST pura para servir um aplicativo móvel (Flutter), não geramos HTML no servidor. Todo o visual fica no celular, e o Spring Boot foca apenas em processar e fornecer dados rápidos.

A Camada DTO (Data Transfer Object): Extremamente necessária. Se você tentar enviar a Entidade Habito inteira (com as senhas do usuário amarradas a ela) para o aplicativo, causará lentidão e falhas de segurança. Os DTOs garantem que o Flutter só receba os dados exatos de que precisa para renderizar as telas.

O Pacote exception: Para pessoas com TDAH, o aplicativo não pode simplesmente falhar ou ficar carregando infinitamente. A API precisa retornar mensagens de erro claras (ex: "Você já marcou esse hábito hoje!") e o GlobalExceptionHandler intercepta qualquer erro do Java e o transforma num JSON legível para o Flutter exibir como um alerta visual amigável.

Separação da Gamificação: Criei um GamificacaoService.java. Em vez de misturar a lógica de acúmulo de XP nos hábitos, este serviço específico lidará com o "Botão Feito", distribuindo os pontos e evoluindo os avatares, mantendo o código altamente organizado.