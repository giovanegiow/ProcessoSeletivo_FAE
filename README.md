# Processo Seletivo FAE — CRUD de Pessoas

API REST para cadastro de alunos e professores, desenvolvida com Java e Spring Boot
a partir da modelagem proposta (Person, Student, Professor, Address e Status).

## Tecnologias utilizadas

- **Linguagem:** Java 17
- **Framework:** Spring Boot 4.2
- **Persistência:** Spring Data JPA / Hibernate
- **Banco de dados:** H2 (em memória — os dados são apagados ao reiniciar a API)
- **Validação:** Jakarta Bean Validation
- **Gerenciador de dependências:** Maven (via Maven Wrapper, não precisa instalar)
- **Testes:** JUnit 5, Mockito (services) e MockMvc (controller)
- **Front-end (opcional):** React + TypeScript + Vite
- **Uso de IA:** Claude Code, como apoio na revisão de código e no aprendizado

## Pré-requisitos

- JDK 17 ou superior
- Node.js 20.19 ou superior (apenas para o front-end)

## Como executar

### API (backend)

Dentro da pasta `demo`:

```bash
./mvnw spring-boot:run      # Git Bash, Linux ou Mac
mvnw.cmd spring-boot:run    # PowerShell ou CMD (Windows)
```

A API sobe em `http://localhost:8080`. Também é possível executar pela IDE,
abrindo `DemoApplication.java` e clicando em executar.

### Front-end (opcional)

Com a API rodando, dentro da pasta `frontend`:

```bash
npm install    # apenas na primeira vez
npm run dev
```

Acesse `http://localhost:5173`.

## Como testar

### Testes unitários

Dentro da pasta `demo`:

```bash
./mvnw test      # Git Bash, Linux ou Mac
mvnw.cmd test    # PowerShell ou CMD (Windows)
```

Os testes ficam em `demo/src/test/java`. Pela IDE, também é possível executar um
teste de cada vez pelo ícone ao lado de cada método.

### Rotas

O arquivo `demo/routes.http` contém todas as requisições, na ordem, com o resultado
esperado de cada uma (incluindo casos de erro). Para usar no VS Code, instale a
extensão **REST Client** e clique em "Send Request" acima de cada requisição.

## Endpoints

| Método | Rota | Descrição |
|---|---|---|
| POST | `/student` | Cadastra um aluno |
| GET | `/student` | Lista os alunos |
| GET | `/student/{id}` | Busca um aluno |
| PUT | `/student/{id}` | Atualiza um aluno |
| DELETE | `/student/{id}` | Remove um aluno |
| POST | `/professor` | Cadastra um professor |
| GET | `/professor` | Lista os professores |
| GET | `/professor/{id}` | Busca um professor |
| PUT | `/professor/{id}` | Atualiza um professor |
| DELETE | `/professor/{id}` | Remove um professor |
| GET | `/person` | Lista todas as pessoas (alunos e professores) |
| GET | `/person/{id}` | Busca uma pessoa |
| GET | `/person/search?name=ana` | Busca pessoas cujo nome contém o texto (sem diferenciar maiúsculas) |
| DELETE | `/person/{id}` | Remove uma pessoa (aluno ou professor) |

Respostas de erro:
- **400** — dados inválidos (a resposta indica cada campo e o motivo)
- **404** — id não encontrado

Exemplo de cadastro de aluno:

```json
{
  "name": "Ana Souza",
  "phoneNumber": "(41) 99999-1234",
  "emailAddress": "ana@email.com",
  "status": "ACTIVE",
  "studentNumber": 2024001,
  "addresses": [
    {
      "street": "Rua A, 10",
      "city": "Curitiba",
      "state": "PR",
      "zipCode": "80000-000",
      "country": "Brasil"
    }
  ]
}
```

## Lógica aplicada

**Herança:** `Student` e `Professor` herdam de `Person`, que guarda os dados comuns
(nome, telefone, e-mail e status). Foi usada a estratégia `JOINED`: existe uma tabela
`person` com os campos comuns e uma tabela para cada subclasse com seus campos
específicos (`student` com `studentNumber` e `photo`; `professor` com `salary`),
ligadas pelo mesmo `id`.

**Relacionamento:** uma pessoa tem um ou mais endereços (1..N). A tabela `address`
possui a coluna `person_id`, que aponta para a pessoa dona. Os endereços são salvos,
atualizados e removidos junto com a pessoa (`cascade` e `orphanRemoval`).

**Status:** enum com os valores `ACTIVE` e `DISABLE`, gravado como texto no banco
(`EnumType.STRING`).

**Decisões técnicas:**
- **`Long` como id:** comporta um volume muito maior de registros que `int` e, por
  ser um objeto, pode ser `null` antes de a entidade ser salva.
- **`BigDecimal` no salário:** evita os erros de arredondamento de `float` e `double`.
- **Foto do aluno:** o front converte a imagem em texto (base64) e ela é enviada no
  próprio JSON do aluno, gravada numa coluna de texto grande (`@Lob`), com limite de 1 MB.
- **Validação:** regras como campos obrigatórios, formato de e-mail, salário positivo
  e pelo menos um endereço são verificadas antes de chegar ao banco.

## Possíveis melhorias

- Usar DTOs em vez de expor as entidades diretamente na API
- Documentação interativa com Swagger/OpenAPI
- Guardar as fotos num serviço de arquivos (ex.: Amazon S3) e salvar só o endereço
  no banco, em vez da imagem inteira
- Banco de dados persistente (ex.: PostgreSQL)
