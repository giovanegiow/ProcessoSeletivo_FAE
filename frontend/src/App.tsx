// Vitrine das telas. Não há navegação nem estado: todas as telas são renderizadas
// em sequência para visualização. Substitua pela sua lógica/roteamento.

import { PersonList } from './components/PersonList';
import { PersonForm } from './components/PersonForm';
// import { ConfirmDialog } from "./components/ConfirmDialog";

export default function App() {
  return (
    <div className="app">
      <header className="topbar">
        <div className="brand">
          <span className="logo" aria-hidden>◆</span>
          <div>
            <h1>Cadastro de Pessoas</h1>
            <p className="muted">Alunos e professores</p>
          </div>
        </div>
      </header>

      <main className="content">
        <h2 className="screen-label">Tela: Listagem</h2>
        <PersonList />

        {/* <h2 className="screen-label">Tela: Listagem vazia</h2>
        <PersonListEmpty /> */}

        <h2 className="screen-label">Tela: Formulário</h2>
        <PersonForm />

        {/* Modal de exclusão: descomente para visualizar
        <ConfirmDialog /> */}
      </main>
    </div>
  );
}
