# Tech Health - Documentação Completa

## 1. Introdução

Este projeto foi desenvolvido como parte do Tech Challenge da fase 3 da FIAP, com o objetivo de criar uma solução robusta e segura para ambientes hospitalares dinâmicos e de comunicação assíncrona. Buscamos atender todos os requisitos do desafio, incluindo:
- Autenticação e autorização com Spring Security, garantindo acesso controlado para médicos, enfermeiros e pacientes.
- Modularidade, separando os serviços de agendamento, notificações e histórico.
- Comunicação assíncrona entre serviços utilizando Kafka, promovendo escalabilidade e integração eficiente.
- Implementação de consultas flexíveis via GraphQL para histórico e agendamento de pacientes.
- Documentação detalhada, arquitetura clara e coleções de teste automatizadas para facilitar validação e manutenção.

A seguir, detalhamos cada aspecto do sistema, explicando as decisões técnicas e funcionais tomadas para garantir que o projeto esteja totalmente aderente ao edital e pronto para avaliação acadêmica.

## 2. Visão Geral do Projeto

O Tech Health nasceu da necessidade de tornar o ambiente hospitalar mais eficiente e seguro. Desde o início, nosso objetivo foi criar um backend modular que permitisse agendar consultas, gerenciar históricos de pacientes e enviar lembretes automáticos, tudo isso respeitando os diferentes perfis de acesso: médicos, enfermeiros e pacientes. A escolha das tecnologias (Spring Boot, Kafka, GraphQL, Docker, etc.) foi feita para garantir escalabilidade, segurança e facilidade de integração.

## 3. Arquitetura

Estruturamos o projeto para separar claramente responsabilidades e facilitar a manutenção. Abaixo, explicamos como cada parte foi pensada:

- **Camada de aplicação:** Aqui ficam os DTOs, mappers e serviços que fazem a ponte entre o domínio e as interfaces (REST/GraphQL).
- **Configurações:** Tudo que envolve auditoria, integração com Kafka, GraphQL e visualização está centralizado para facilitar ajustes.
- **Domínio:** Os modelos, repositórios e regras de negócio estão organizados para refletir o fluxo real de uma clínica.
- **Exceções:** Criamos exceções customizadas para garantir respostas claras e seguras.
- **Infraestrutura:** Os controllers REST e GraphQL, além dos componentes de segurança, garantem que cada perfil acesse apenas o que precisa.

A comunicação entre serviços é feita de forma assíncrona, usando Kafka. Por exemplo, ao agendar uma consulta, uma mensagem é enviada para o serviço de notificações, que então dispara o lembrete ao paciente.

## 4. Segurança

Desde o início, a segurança foi prioridade. Implementamos autenticação JWT com Spring Security, garantindo que apenas usuários autenticados possam acessar funcionalidades sensíveis. Perfis de acesso foram definidos para médicos, enfermeiros e pacientes, cada um com permissões específicas:
- Médicos: visualizam e editam históricos.
- Enfermeiros: registram consultas e acessam históricos.
- Pacientes: visualizam apenas suas consultas.

As permissões são controladas por roles e anotadas diretamente nos endpoints e resolvers GraphQL.

## 5. Endpoints REST

A API REST foi desenhada para ser intuitiva e segura:
- **Autenticação:**
  - `POST /api/auth/login`: Login e obtenção de token JWT.
  - `POST /api/auth/register`: Cadastro de novos usuários.

Os endpoints de autenticação são públicos, enquanto os demais exigem token JWT.

## 6. GraphQL

Optamos pelo GraphQL para consultas flexíveis sobre o histórico de pacientes e agendamento de consultas. As queries e mutations permitem, por exemplo:
- Buscar todas as consultas de um paciente.
- Registrar novas consultas (médicos/enfermeiros).
- Atualizar consultas.
Para exemplos completos de payloads (request/response) e permissões, consulte as seções 10.1, 10.2, 10.3 e 10.4.

## 7. Mensageria (Kafka)

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

## 8. Execução

## 8.1 Pré-requisitos
- **JDK 21** instalado e configurado (`JAVA_HOME`)
- **Docker** e **Docker Compose** instalados
- **Maven** (ou use o wrapper `./mvnw`)
- **Postman** (opcional, para testes)

## 8.2 Configuração do ambiente
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

## 8.3 Subindo o banco de dados e pgAdmin
No terminal, execute:
```shell
docker-compose up -d
```
- O PostgreSQL ficará disponível na porta definida em `DB_PORT` (padrão: 5433).
- O pgAdmin ficará disponível em `http://localhost:5050`.
Acesse o pgAdmin com o email e senha definidos no `.env`.

## 8.4 Rodando o backend
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

## 8.5 Estrutura do projeto
```
techhealth/
├── src/main/java pump/...         # Código fonte Java (controllers, services, models)
├── src/main/resources/
│   └── application.properties # Configurações do backend
├── .env                      # Variáveis de ambiente para Docker
├── docker-compose.yaml       # Orquestração dos containers PostgreSQL e pgAdmin
├── README.md                 # Guia rápido do projeto
└── documentation.md          # Documentação técnica detalhada
```

## 8.6 Variáveis de ambiente
- **DB_PORT**: Porta do PostgreSQL
- **PGADMIN_PORT**: Porta do pgAdmin
- **DB_USERNAME**: Usuário do banco
- **DB_PASSWORD**: Senha do banco
- **DB_NAME**: Nome do banco
- **DB_EMAIL**: Email do pgAdmin

## 8.7 Observações
- Certifique-se de que o JDK 21 está instalado e configurado.
- O banco de dados deve estar rodando antes de iniciar o backend.
- Para dúvidas sobre rotas, consulte os controllers do projeto.

## 9. Coleções de Teste (Postman)

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

## 10. Operações de Consultas por Perfil (GraphQL)

Todas as operações abaixo estão disponíveis no endpoint `/graphql` (ver configuração em `spring.graphql.path`). Os exemplos usam os tipos definidos no schema (`src/main/resources/graphql/schema.graphqls`) e refletem as permissões aplicadas no resolver (`ConsultationGraphQLController`).

- **Mensagem e notificações por e‑mail**: após criar ou atualizar uma consulta, o serviço publica um evento no Kafka e o serviço de mensageria envia um e‑mail usando o servidor SMTP fake configurado em `localhost:1025`.

## 10.1 Paciente (PATIENT)

- **consultationByPatient**
  - **Descrição**: obtém os detalhes de uma consulta do próprio paciente por ID.
  - **Permissão**: PATIENT
  - **Request**:
    ```graphql
    query {
      consultationByPatient(idConsultation: 10) {
        id
        patientReport
        consultationDate
        medic { id name }
        nurse { id name }
        patient { id name }
        audit { createdAt updatedAt }
      }
    }
    ```
  - **Response**:
    ```json
    {
      "data": {
        "consultationByPatient": {
          "id": "10",
          "patientReport": "Dores de cabeça recorrentes",
          "consultationDate": "2024-09-20T10:00:00",
          "medic": { "id": "1", "name": "Dr. Fulano" },
          "nurse": { "id": "2", "name": "Enfermeira Beltrana" },
          "patient": { "id": "3", "name": "Paciente Silva" },
          "audit": { "createdAt": "2024-09-19T09:00:00", "updatedAt": null }
        }
      }
    }
    ```

- **getConsultationsForPatient**
  - **Descrição**: lista todas as consultas do paciente autenticado.
  - **Permissão**: PATIENT
  - **Request**:
    ```graphql
    query {
      getConsultationsForPatient {
        id
        consultationDate
        medic { id name }
        nurse { id name }
      }
    }
    ```
  - **Response (resumo)**:
    ```json
    { "data": { "getConsultationsForPatient": [ { "id": "10", "consultationDate": "2024-09-20T10:00:00", "medic": { "id": "1", "name": "Dr. Fulano" }, "nurse": { "id": "2", "name": "Enfermeira Beltrana" } } ] } }
    ```

- **findAllMyConsultations**
  - **Descrição**: sinônimo de lista de consultas do próprio paciente.
  - **Permissão**: PATIENT

- **findMyConsultationById**
  - **Descrição**: obtém uma consulta específica do próprio paciente por ID.
  - **Permissão**: PATIENT

- **findMyFutureConsultations**
  - **Descrição**: lista as consultas futuras do próprio paciente.
  - **Permissão**: PATIENT

- **futureConsultations(patientId)**
  - **Descrição**: lista consultas futuras para um `patientId` informado.
  - **Permissão**: PATIENT, DOCTOR, NURSE

Observação: o acesso do paciente é restrito ao próprio contexto; controles adicionais são aplicados no serviço para garantir que um paciente não visualize dados de terceiros.

## 10.2 Médico (DOCTOR)

- **findAllConsultations**
  - **Descrição**: lista todas as consultas do sistema.
  - **Permissão**: DOCTOR, NURSE
  - **Request**:
    ```graphql
    query {
      findAllConsultations {
        id
        patientReport
        consultationDate
        medic { id name }
        nurse { id name }
        patient { id name }
      }
    }
    ```
  - **Response (resumo)**:
    ```json
    { "data": { "findAllConsultations": [ { "id": "10", "patientReport": "Dores de cabeça", "consultationDate": "2024-09-20T10:00:00", "medic": { "id": "1", "name": "Dr. Fulano" }, "nurse": { "id": "2", "name": "Enfermeira" }, "patient": { "id": "3", "name": "Paciente" } } ] } }
    ```

- **consultationById(id)**
  - **Descrição**: obtém detalhes de uma consulta por ID.
  - **Permissão**: DOCTOR, NURSE

- **findPastConsultationsByDoctor(patientId)**
  - **Descrição**: lista as consultas passadas daquele paciente, para uso do médico.
  - **Permissão**: DOCTOR

- **findFutureConsultationsByDoctor(patientId)**
  - **Descrição**: lista as consultas futuras daquele paciente, para uso do médico.
  - **Permissão**: DOCTOR

- **futureConsultations(patientId)**
  - **Descrição**: lista as consultas futuras para um paciente específico.
  - **Permissão**: PATIENT, DOCTOR, NURSE

- **createConsultation(dto: ConsultationRequest)**
  - **Descrição**: cria uma nova consulta (gera evento e dispara e‑mail via SMTP fake na porta 1025).
  - **Permissão**: DOCTOR, NURSE
  - **Request**:
    ```graphql
    mutation {
      createConsultation(
        dto: {
          idPatient: "3"
          idMedic: "1"
          idNurse: "2"
          patientReport: "Retorno de exame"
          consultationDate: "2024-10-15T14:30:00"
        }
      ) {
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
  - **Response**:
    ```json
    {
      "data": {
        "createConsultation": {
          "id": "42",
          "patientReport": "Retorno de exame",
          "consultationDate": "2024-10-15T14:30:00",
          "medic": { "id": "1", "name": "Dr. Fulano" },
          "nurse": { "id": "2", "name": "Enfermeira" },
          "patient": { "id": "3", "name": "Paciente" },
          "audit": { "createdAt": "2024-10-10T09:00:00" }
        }
      }
    }
    ```

- **updateConsultation(id, dto: ConsultationUpdateRequest)**
  - **Descrição**: atualiza campos da consulta (ex.: `patientReport`, `consultationDate`).
  - **Permissão**: DOCTOR, NURSE
  - **Request**:
    ```graphql
    mutation {
      updateConsultation(
        id: 42,
        dto: { patientReport: "Ajuste de prescrição", consultationDate: "2024-10-20T09:00:00" }
      ) {
        id
        patientReport
        consultationDate
        audit { updatedAt }
      }
    }
    ```
  - **Response**:
    ```json
    { "data": { "updateConsultation": { "id": "42", "patientReport": "Ajuste de prescrição", "consultationDate": "2024-10-20T09:00:00", "audit": { "updatedAt": "2024-10-12T11:00:00" } } } }
    ```

## 10.3 Enfermeiro (NURSE)

- **findAllConsultations**
  - **Descrição**: lista todas as consultas do sistema.
  - **Permissão**: DOCTOR, NURSE

- **consultationById(id)**
  - **Descrição**: obtém detalhes de uma consulta por ID.
  - **Permissão**: DOCTOR, NURSE

- **futureConsultations(patientId)**
  - **Descrição**: lista consultas futuras para um paciente específico.
  - **Permissão**: PATIENT, DOCTOR, NURSE

- **createConsultation(dto: ConsultationRequest)**
  - **Descrição**: cria uma nova consulta (gera evento e dispara e‑mail via SMTP fake na porta 1025).
  - **Permissão**: DOCTOR, NURSE

- **updateConsultation(id, dto: ConsultationUpdateRequest)**
  - **Descrição**: atualiza os dados de uma consulta.
  - **Permissão**: DOCTOR, NURSE

Notas:
- Os exemplos acima são compatíveis com os tipos do schema GraphQL (`ConsultationRequest`, `ConsultationUpdateRequest`, `ConsultationResponse`).
- O controle de autorização é aplicado via anotações como `@PreAuthorize` no `ConsultationGraphQLController`.
- A interface GraphiQL está habilitada em `/graphiql` para facilitar os testes.

## 10.4 Tabela de Permissões (Consultas - GraphQL)

Todas as operações são expostas em `/graphql` e exigem autenticação via JWT. A tabela abaixo resume as permissões por Role conforme o resolver GraphQL:

| Operação                                  | Descrição resumida                                             | PATIENT | DOCTOR | NURSE |
|-------------------------------------------|----------------------------------------------------------------|:-------:|:------:|:-----:|
| consultationByPatient                      | Detalhe de consulta do próprio paciente                        |   ✔    |   ✖    |   ✖   |
| getConsultationsForPatient                 | Lista consultas do próprio paciente                            |   ✔    |   ✖    |   ✖   |
| findAllMyConsultations                     | Lista todas as consultas do próprio paciente                   |   ✔    |   ✖    |   ✖   |
| findMyConsultationById                     | Detalhe de consulta do próprio paciente por ID                 |   ✔    |   ✖    |   ✖   |
| findMyFutureConsultations                  | Lista futuras do próprio paciente                              |   ✔    |   ✖    |   ✖   |
| futureConsultations(patientId)             | Lista futuras para um paciente específico                      |   ✔    |   ✔    |   ✔   |
| consultationById                           | Detalhe de consulta por ID                                     |   ✖    |   ✔    |   ✔   |
| findAllConsultations                       | Lista todas as consultas                                       |   ✖    |   ✔    |   ✔   |
| findPastConsultationsByDoctor(patientId)   | Lista passadas de um paciente (visão do médico)                |   ✖    |   ✔    |   ✖   |
| findFutureConsultationsByDoctor(patientId) | Lista futuras de um paciente (visão do médico)                 |   ✖    |   ✔    |   ✖   |
| createConsultation(dto)                    | Cria consulta                                                  |   ✖    |   ✔    |   ✔   |
| updateConsultation(id, dto)                | Atualiza consulta                                              |   ✖    |   ✔    |   ✔   |

Observações:
- As permissões acima são definidas por `@PreAuthorize` no `ConsultationGraphQLController`.
- A mutation `deleteConsultation` existe no schema, mas não há implementação correspondente no controller no momento desta documentação.

## 10.5 Fluxo completo: Agendamento → Kafka → E‑mail

Este fluxo ocorre quando uma consulta é criada ou atualizada via GraphQL. Resumo das etapas:

## 10.5.1. **Agendamento (GraphQL em `/graphql`)**
   - Chamada à mutation `createConsultation(dto: ConsultationRequest!)` ou `updateConsultation(id, dto)`.
   - Exemplo de criação:
     ```graphql
     mutation {
       createConsultation(
         dto: {
           idPatient: "3"
           idMedic: "1"
           idNurse: "2"
           patientReport: "Retorno de exame"
           consultationDate: "2024-10-15T14:30:00"
         }
       ) {
         id
         consultationDate
         patient { id name email }
         medic { id name }
       }
     }
     ```

## 10.5.2. **Publicação no Kafka**
   - O serviço de domínio persiste a consulta e publica o DTO no tópico `consultation-topic`.
   - Estrutura típica do payload publicado:
     ```json
     {
       "id": 42,
       "patientReport": "Retorno de exame",
       "consultationDate": "2024-10-15T14:30:00",
       "medic": { "id": 1, "name": "Dr. Fulano" },
       "nurse": { "id": 2, "name": "Enfermeira" },
       "patient": { "id": 3, "name": "Paciente", "email": "paciente@exemplo.com" }
     }
     ```

## 10.5.3. **Consumo e envio de e‑mail**
   - O `NotificationService` consome do tópico `consultation-topic` e monta uma mensagem de confirmação.
   - O `EmailService` envia o e‑mail usando o servidor SMTP fake em `localhost:1025`.
   - Exemplo de conteúdo do e‑mail (HTML simplificado):
     ```html
     <h1>Olá, Paciente!</h1>
     <p>Sua consulta com o(a) Dr(a). Fulano foi confirmada para: <strong>15/10/2024 às 14:30</strong>.</p>
     ```


## 10.5.4. Envio de E-mails Fake para Testes

O sistema envia notificações por e-mail para pacientes após o agendamento ou atualização de consultas. Para facilitar testes em ambiente de desenvolvimento, utilizamos um servidor SMTP fake rodando localmente na porta `1025`. Isso permite visualizar os e-mails enviados sem utilizar serviços reais de e-mail.

### Configuração

No arquivo `application.properties`, as seguintes propriedades garantem o uso do SMTP fake:

```properties
spring.mail.host=localhost
spring.mail.port=1025
spring.mail.username=
spring.mail.password=
spring.mail.properties.mail.smtp.auth=false
```

Recomenda-se utilizar ferramentas como [MailHog](https://github.com/mailhog/MailHog) ou [FakeSMTP](https://nilhcem.com/FakeSMTP/) para capturar e visualizar os e-mails enviados durante os testes. Basta subir o serviço SMTP fake (por exemplo, via Docker) e acessar a interface web para visualizar as mensagens.

### Funcionamento

- Sempre que uma consulta é criada ou atualizada, o serviço de notificações dispara um e-mail para o paciente usando o servidor SMTP fake.
- Nenhum e-mail real é enviado; todos os e-mails podem ser visualizados na interface do MailHog/FakeSMTP.

### Observação

Para produção, altere as configurações do SMTP para um serviço de e-mail real e seguro.

## 11. **Observações operacionais**
   - Broker Kafka esperado em `localhost:9092`.
   - Para inspecionar as requisições GraphQL, utilize `/graphiql`.
   - Todas as operações GraphQL estão disponíveis em `/graphql`.