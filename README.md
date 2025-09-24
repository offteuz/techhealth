# 🩺 TechHealth

Sistema de gerenciamento de usuários para área da saúde, com autenticação JWT, integração com PostgreSQL, administração via pgAdmin, agendamento de consultas, histórico de pacientes e envio de notificações automáticas via Kafka.

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
- [📦 Mensageria e Kafka](#-mensageria-e-kafka)
- [🗂️ GraphQL](#-graphql)
- [📄 Documentação técnica](#-documentação-técnica)

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

### 👤 Usuários

- **GET /api/user/find-by-id/{idUser}**  
  Buscar usuário por ID.
- **GET /api/user/find-all**  
  Listar todos usuários.

### 🩺 Consultas (GraphQL)

- **Mutation:** Criar, atualizar e remover consultas
- **Query:** Buscar consultas por paciente, médico ou enfermeiro

---

## 📦 Mensageria e Kafka

- Utiliza Kafka para comunicação assíncrona entre serviços.
- Tópico principal: `consultation-topic`.
- Mensagens publicadas ao criar/editar consultas, consumidas pelo serviço de notificações.

Exemplo de payload:
```json
{
  "id": 1,
  "patientReport": "Relatório do paciente",
  "consultationDate": "2024-09-20T10:00:00",
  "medic": { "id": 1, "name": "Dr. Fulano" },
  "nurse": { "id": 2, "name": "Enfermeira" },
  "patient": { "id": 3, "name": "Paciente" },
  "audit": { "createdAt": "2024-09-20T09:00:00" }
}
```

---

## 🗂️ GraphQL

- Permite consultas flexíveis sobre histórico e agendamento de pacientes.
- Queries e mutations disponíveis para médicos, enfermeiros e pacientes.

Exemplo de mutation:
```graphql
mutation {
  createConsultation(dto: { idPatient: "3", idMedic: "1", idNurse: "2", patientReport: "Relatório", consultationDate: "2024-09-20T10:00:00" }) {
    id
    patientReport
    consultationDate
    medic { id name }
    nurse { id name }
    patient { id name }
    audit { createdAt }
  }
}
```

---

## 🧪 Testando com Postman

- Coleção Postman disponível em `postman/Tech Health Collection.postman_collection.json`.
- Testes de autenticação, consultas, permissões e fluxos completos.

---

## 📁 Estrutura do projeto

```
techhealth/
├── src/main/java/...         # Código fonte Java (controllers, services, models)
├── src/main/resources/
│   └── application.properties # Configurações do backend
├── .env                      # Variáveis de ambiente para Docker
├── docker-compose.yaml       # Orquestração dos containers PostgreSQL e pgAdmin
├── README.md                 # Guia rápido do projeto
└── documentation.md          # Documentação técnica detalhada
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

## 📄 Documentação técnica

- Para detalhes completos de arquitetura, segurança, endpoints, mensageria e testes, consulte o arquivo `documentation.md`.

---

## 👨‍💻 Autores

- Matteus Santos de Abreu ([GitHub](https://github.com/Nexusf1re))
- Francisco Aurizelio de Sousa ([GitHub](https://github.com/faurizel))
- Lucas Herculano Amaro ([GitHub](https://github.com/LucasHerculanoAmaro))
- Matheus Jesus de Souza ([GitHub](https://github.com/offteuz))
- Vitor Silva França (vitorsilvafranca.com)

---

**TechHealth** ©