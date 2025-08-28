# 🩺 TechHealth

Sistema de gerenciamento de usuários para área da saúde, com autenticação JWT, integração com PostgreSQL e administração via pgAdmin.

---

## 📋 Sumário

- [⚙️ Pré-requisitos](#️-pré-requisitos)
- [🛠️ Configuração do ambiente](#️-configuração-do-ambiente)
- [🐳 Subindo o banco de dados e pgAdmin](#-subindo-o-banco-de-dados-e-pgadmin)
- [🚀 Rodando o backend](#-rodando-o-backend)
- [🔗 Endpoints principais](#-endpoints-principais)
- [🧪 Testando com Postman](#-testando-com-postman)
- [📁 Estrutura do projeto](#-estrutura-do-projeto)
- [🔑 Variáveis de ambiente](#-variáveis-de-ambiente)
- [⚡ Configurações adicionais](#-configurações-adicionais)

---

## ⚙️ Pré-requisitos

- **JDK 21** instalado e configurado (`JAVA_HOME`)
- **Docker** e **Docker Compose** instalados
- **Maven** (ou use o wrapper `./mvnw`)
- **Postman** (opcional, para testes)

---

## 🛠️ Configuração do ambiente

1. Edite o arquivo `.env` na raiz do projeto com suas configurações:
    ```properties
    DB_PORT="5433"
    PGADMIN_PORT="5050"
    DB_USERNAME="admin"
    DB_EMAIL="admin@email.com"
    DB_PASSWORD="admin"
    DB_NAME="database"
    ```

2. O arquivo `application.properties` já está configurado para usar essas variáveis.

---

## 🐳 Subindo o banco de dados e pgAdmin

No terminal, execute:
```shell
docker-compose up -d
```

- O PostgreSQL ficará disponível na porta definida em `DB_PORT` (padrão: 5433).
- O pgAdmin ficará disponível em `http://localhost:5050`.

Acesse o pgAdmin com o email e senha definidos no `.env`.

---

## 🚀 Rodando o backend

1. Certifique-se de estar usando o **JDK 21**.
2. No terminal, execute:
    ```shell
    ./mvnw spring-boot:run
    ```
    ou
    ```shell
    mvn spring-boot:run
    ```
3. O backend estará disponível em `http://localhost:8090`.

---

## 🔗 Endpoints principais

### 🔐 Autenticação

- **POST /api/auth/register**  
  Cria um novo usuário.
- **POST /api/auth/login**  
  Autentica e retorna um token JWT.

### 🩺 Testes de autenticação

- **GET /api/test**  
  Qualquer usuário autenticado.
- **GET /api/test/doctor**  
  Apenas usuários com papel "DOCTOR".
- **GET /api/test/nurse**  
  Apenas usuários com papel "NURSE".
- **GET /api/test/patient**  
  Apenas usuários com papel "PATIENT".

### 👤 Usuários

- **GET /api/user/find-by-id/{idUser}**  
  Buscar usuário por ID (DOCTOR).
- **GET /api/user/find-all**  
  Listar todos usuários (DOCTOR, NURSE).
- **DELETE /api/user/delete/{idUser}**  
  Deletar usuário por ID (DOCTOR).
- **PATCH /api/user/update/{idUser}**  
  Atualizar usuário por ID (DOCTOR).

---

## 🧪 Testando com Postman

1. **Registrar usuário:**  
   POST `http://localhost:8090/api/auth/register`  
   Body (JSON):
   ```json
   {
     "name": "Nome Completo",
     "userName": "usuario",
     "email": "seu@email.com",
     "password": "sua_senha",
     "address": {
       "cep": "12345678",
       "street": "Rua Exemplo",
       "number": "123",
       "neighborhood": "Bairro",
       "city": "Cidade",
       "state": "Estado",
       "country": "País"
     },
     "role": "DOCTOR"
   }
   ```

2. **Login:**  
   POST `http://localhost:8090/api/auth/login`  
   Body (JSON):
   ```json
   {
     "email": "seu@email.com",
     "password": "sua_senha"
   }
   ```
   Copie o token JWT retornado.

3. **Endpoints protegidos:**  
   Adicione o header:
   ```
   Authorization: Bearer SEU_TOKEN
   ```

---

## 📁 Estrutura do projeto

```
techhealth/
├── src/main/java/...         # Código fonte Java (controllers, services, models)
├── src/main/resources/
│   └── application.properties # Configurações do backend
├── .env                      # Variáveis de ambiente para Docker
├── docker-compose.yaml       # Orquestração dos containers PostgreSQL e pgAdmin
└── README.md                 # Documentação do projeto
```

---

## 🔑 Variáveis de ambiente

- **DB_PORT**: Porta do PostgreSQL
- **PGADMIN_PORT**: Porta do pgAdmin
- **DB_USERNAME**: Usuário do banco
- **DB_PASSWORD**: Senha do banco
- **DB_NAME**: Nome do banco
- **DB_EMAIL**: Email do pgAdmin

---

## ⚡ Configurações adicionais

- O backend está configurado para rodar na porta **8090** (`server.port=8090`).
- O banco de dados está em `localhost:5433/database`.
- O JWT usa o segredo definido em `application.properties`.

---

## 💡 Observações

- Certifique-se de que o JDK 21 está instalado e configurado.
- O banco de dados deve estar rodando antes de iniciar o backend.
- Para dúvidas sobre rotas, consulte os controllers do projeto.

---

**TechHealth** ©