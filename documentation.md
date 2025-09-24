# Tech Health - Documentação Completa

## Introdução

Este projeto foi desenvolvido como parte do Tech Challenge da fase 3 da FIAP, com o objetivo de criar uma solução robusta e segura para ambientes hospitalares dinâmicos e de comunicação assíncrona. Buscamos atender todos os requisitos do desafio, incluindo:
- Autenticação e autorização com Spring Security, garantindo acesso controlado para médicos, enfermeiros e pacientes.
- Modularidade, separando os serviços de agendamento, notificações e histórico.
- Comunicação assíncrona entre serviços utilizando Kafka, promovendo escalabilidade e integração eficiente.
- Implementação de consultas flexíveis via GraphQL para histórico e agendamento de pacientes.
- Documentação detalhada, arquitetura clara e coleções de teste automatizadas para facilitar validação e manutenção.

A seguir, detalhamos cada aspecto do sistema, explicando as decisões técnicas e funcionais tomadas para garantir que o projeto esteja totalmente aderente ao edital e pronto para avaliação acadêmica.

## 1. Visão Geral do Projeto

O Tech Health nasceu da necessidade de tornar o ambiente hospitalar mais eficiente e seguro. Desde o início, nosso objetivo foi criar um backend modular que permitisse agendar consultas, gerenciar históricos de pacientes e enviar lembretes automáticos, tudo isso respeitando os diferentes perfis de acesso: médicos, enfermeiros e pacientes. A escolha das tecnologias (Spring Boot, Kafka, GraphQL, Docker, etc.) foi feita para garantir escalabilidade, segurança e facilidade de integração.

## 2. Arquitetura

Estruturamos o projeto para separar claramente responsabilidades e facilitar a manutenção. Abaixo, explicamos como cada parte foi pensada:

- **Camada de aplicação:** Aqui ficam os DTOs, mappers e serviços que fazem a ponte entre o domínio e as interfaces (REST/GraphQL).
- **Configurações:** Tudo que envolve auditoria, integração com Kafka, GraphQL e visualização está centralizado para facilitar ajustes.
- **Domínio:** Os modelos, repositórios e regras de negócio estão organizados para refletir o fluxo real de uma clínica.
- **Exceções:** Criamos exceções customizadas para garantir respostas claras e seguras.
- **Infraestrutura:** Os controllers REST e GraphQL, além dos componentes de segurança, garantem que cada perfil acesse apenas o que precisa.

A comunicação entre serviços é feita de forma assíncrona, usando Kafka. Por exemplo, ao agendar uma consulta, uma mensagem é enviada para o serviço de notificações, que então dispara o lembrete ao paciente.

## 3. Segurança

Desde o início, a segurança foi prioridade. Implementamos autenticação JWT com Spring Security, garantindo que apenas usuários autenticados possam acessar funcionalidades sensíveis. Perfis de acesso foram definidos para médicos, enfermeiros e pacientes, cada um com permissões específicas:
- Médicos: visualizam e editam históricos.
- Enfermeiros: registram consultas e acessam históricos.
- Pacientes: visualizam apenas suas consultas.

As permissões são controladas por roles e anotadas diretamente nos endpoints e resolvers GraphQL.

## 4. Endpoints REST

A API REST foi desenhada para ser intuitiva e segura:
- **Autenticação:**
  - `POST /api/auth/login`: Login e obtenção de token JWT.
  - `POST /api/auth/register`: Cadastro de novos usuários.
- **Usuários:**
  - `GET /api/user/find-by-id/{idUser}`: Busca por ID.
  - `GET /api/user/find-all`: Lista todos os usuários.

Os endpoints de autenticação são públicos, enquanto os demais exigem token JWT.

## 5. GraphQL

Optamos pelo GraphQL para consultas flexíveis sobre o histórico de pacientes e agendamento de consultas. As queries e mutations permitem, por exemplo:
- Buscar todas as consultas de um paciente.
- Registrar novas consultas (médicos/enfermeiros).
- Atualizar ou remover consultas.

Exemplo de mutation para criar uma consulta:
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

## 6. Mensageria (Kafka)

Para garantir que notificações sejam enviadas sem travar o fluxo principal, integramos Kafka. Sempre que uma consulta é criada ou editada, o serviço de agendamento publica uma mensagem no tópico `consultation-topic`. O serviço de notificações consome essa mensagem e envia o lembrete ao paciente.

Exemplo de payload enviado:
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

## 7. Execução

### Pré-requisitos
- **JDK 21** instalado e configurado (`JAVA_HOME`)
- **Docker** e **Docker Compose** instalados
- **Maven** (ou use o wrapper `./mvnw`)
- **Postman** (opcional, para testes)

### Configuração do ambiente
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

### Subindo o banco de dados e pgAdmin
No terminal, execute:
```shell
docker-compose up -d
```
- O PostgreSQL ficará disponível na porta definida em `DB_PORT` (padrão: 5433).
- O pgAdmin ficará disponível em `http://localhost:5050`.
Acesse o pgAdmin com o email e senha definidos no `.env`.

### Rodando o backend
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

### Estrutura do projeto
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

### Variáveis de ambiente
- **DB_PORT**: Porta do PostgreSQL
- **PGADMIN_PORT**: Porta do pgAdmin
- **DB_USERNAME**: Usuário do banco
- **DB_PASSWORD**: Senha do banco
- **DB_NAME**: Nome do banco
- **DB_EMAIL**: Email do pgAdmin

### Observações
- Certifique-se de que o JDK 21 está instalado e configurado.
- O banco de dados deve estar rodando antes de iniciar o backend.
- Para dúvidas sobre rotas, consulte os controllers do projeto.

## 8. Coleções de Teste (Postman)

Preparamos uma coleção Postman para facilitar os testes. Ela cobre:
- Cadastro e login de usuários
- Criação, busca, atualização e remoção de consultas via GraphQL
- Testes de permissões e fluxos completos

Exemplo de requisição GraphQL:
```json
{
  "query": "mutation { createConsultation(dto: { idPatient: \"3\", idMedic: \"1\", idNurse: \"2\", patientReport: \"Relatório\", consultationDate: \"2024-09-20T10:00:00\" }) { id patientReport consultationDate medic { id name } nurse { id name } patient { id name } audit { createdAt } } }"
}
```

---

> Todo o projeto foi pensado para ser didático, seguro e fácil de evoluir. Qualquer dúvida, consulte os arquivos de código ou a coleção Postman incluída.
