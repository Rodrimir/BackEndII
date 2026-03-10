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
