# ProjetoBackEnd20261

github: https://github.com/gillgonzales/cstsi_dbe2_202601_php_laravel

***Banco de dados***

DDL:
CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    nickname VARCHAR(50)
);

(vinculada ao usuário)
CREATE TABLE rotinas (
    id SERIAL PRIMARY KEY,
    usuario_id INT NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT
);

CREATE TABLE tipos_tarefa (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
);

CREATE TABLE tarefas (
    id SERIAL PRIMARY KEY,
    usuario_id INT NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    rotina_id INT REFERENCES rotinas(id) ON DELETE SET NULL,
    tipo_tarefa_id INT REFERENCES tipos_tarefa(id) ON DELETE SET NULL,
    titulo VARCHAR(150) NOT NULL,
    descricao TEXT,
    data_planejada DATE NOT NULL,
    inicio TIMESTAMP,
    fim TIMESTAMP,
    concluida BOOLEAN DEFAULT FALSE,
    duracao INTERVAL GENERATED ALWAYS AS (fim - inicio) STORED
);

## 🚀 Como Usar Docker Compose

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
# Iniciar em modo foreground (vê os logs)
docker compose up

# Iniciar em background (modo daemon)
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

## 📁 Estrutura do Projeto

meu-projeto-spring/
├── build.gradle                       # O seu 'composer.json' (gerencia dependências)
├── compose.yaml                       # Continua na raiz
├── Dockerfile                         # Continua na raiz
└── src/
    └── main/
        ├── java/com/rodrigo/          # O núcleo da sua aplicação (seu antigo src/app/)
        │   ├── Application.java       # Substitui o public/index.php e o core/App.php
        │   │
        │   ├── controller/            # Equivalente ao src/app/controllers/
        │   │   └── ProdutoController.java
        │   │
        │   ├── model/                 # Equivalente ao src/app/models/
        │   │   └── Produto.java
        │   │
        │   ├── repository/            # O Spring faz o acesso ao banco aqui. 
        │   │   └── ProdutoRepository.java
        │   │
        │   └── service/               # Onde as regras de negócio vivem.
        │       └── ProdutoService.java
        │
        └── resources/                 
            ├── application.properties # Substitui o config/, database/Connection.php e Routes
            │
            ├── static/                # Arquivos públicos: CSS, JS, Imagens
            │
            └── templates/             # Equivalente ao src/app/views/ e public/templates/
