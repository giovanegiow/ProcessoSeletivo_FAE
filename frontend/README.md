# Frontend — Cadastro de Pessoas (visual)

Apenas a camada visual (JSX + CSS) do CRUD de `Person` / `Student` / `Professor` / `Address`.
Não há estado, chamadas de API nem validação: os dados exibidos são placeholders.

```bash
npm install
npm run dev
```

## Estrutura

- `src/components/PersonList.tsx` — listagem (abas, busca, tabela) e estado vazio
- `src/components/PersonForm.tsx` — formulário (tipo, dados pessoais, aluno, professor)
- `src/components/AddressFields.tsx` — bloco de endereços (1..*)
- `src/components/ConfirmDialog.tsx` — modal de exclusão
- `src/index.css` — estilos (tema claro/escuro, responsivo; tabela vira cards em telas < 760px)

Os `name` dos inputs seguem os atributos do diagrama (`name`, `phoneNumber`, `emailAddress`,
`status`, `studentNumber`, `photo`, `salary`, `street`, `city`, `state`, `zipCode`, `country`).
