// Tela de listagem — apenas visual. Os dados abaixo são placeholders estáticos.
import { useEffect, useState } from 'react';

type Person = {
  id: number;
  name: string;
  phoneNumber: string;
  emailAddress: string;
  status: 'ACTIVE' | 'DISABLE';
  type: 'STUDENT' | 'PROFESSOR';
};

export function PersonList() {
  const numPerson = SearchTotalPerson();

  const numStudents = numPerson.filter((p) => p.type === 'STUDENT').length;
  const numProfessors = numPerson.filter((p) => p.type === 'PROFESSOR').length;
  
  return (
    <section className="card">
      <div className="toolbar">
        <div className="tabs" role="tablist">
          <button role="tab" className="active">
            Todos <span className="count">{numPerson.length}</span>
          </button>
          <button role="tab">
            Alunos <span className="count">{numStudents}</span>
          </button>
          <button role="tab">
            Professores <span className="count">{numProfessors}</span>
          </button>
        </div>
        <div className="toolbar-right">
          <input type="search" className="search" placeholder="Buscar por nome, e-mail ou matrícula" />
          <button className="btn btn-primary">+ Nova pessoa</button>
        </div>
      </div>

      <div className="table-wrap">
        <table>
          <thead>
            <tr>
              <th>Nome</th>
              <th>Tipo</th>
              <th>Contato</th>
              <th>Detalhe</th>
              <th>Cidade</th>
              <th>Status</th>
              <th aria-label="Ações" />
            </tr>
          </thead>
          <tbody>
            <SearchPerson />
          </tbody>
        </table>
      </div>
    </section>
  );
}

/** Estado vazio da listagem — use quando não houver registros. */
export function PersonListEmpty() {
  return (
    <section className="card">
      <div className="empty">
        <p>Nenhuma pessoa cadastrada ainda.</p>
        <button className="btn btn-primary">Cadastrar a primeira</button>
      </div>
    </section>
  );
}

function iniciais(nome: string) {
  return nome
    .split(' ')
    .filter(Boolean)
    .slice(0, 2)
    .map((parte) => parte[0].toUpperCase())
    .join('');
}

function SearchPerson() {
  const [pessoas, setPessoas] = useState<Person[]>([]);
  const [erro, setErro] = useState<string | null>(null);

  useEffect(() => {
    fetch('/api/person')
      .then((resposta) => {
        if (!resposta.ok) throw new Error(`HTTP ${resposta.status}`);
        return resposta.json();
      })
      .then((dados: Person[]) => setPessoas(dados))
      .catch((e) => setErro(String(e)));
  }, []);

  if (erro) {
    return (
      <tr>
        <td colSpan={7} className="error">Erro ao buscar pessoas: {erro}</td>
      </tr>
    );
  }

  if (pessoas.length === 0) {
    return (
      <tr>
        <td colSpan={7} className="empty">Nenhuma pessoa cadastrada ainda.</td>
      </tr>
    );
  }

  return (
    <>
      {pessoas.map((pessoa) => (
        <tr key={pessoa.id}>
          <td data-label="Nome">
            <div className="person-cell">
              <div className="avatar"><span>{iniciais(pessoa.name)}</span></div>
              <span>{pessoa.name}</span>
            </div>
          </td>
          <td data-label="Tipo">—</td>
          <td data-label="Contato">
            <div>
              <div>{pessoa.emailAddress}</div>
              <div className="muted">{pessoa.phoneNumber}</div>
            </div>
          </td>
          <td data-label="Detalhe">—</td>
          <td data-label="Cidade">—</td>
          <td data-label="Status">
            {pessoa.status === 'ACTIVE'
              ? <span className="badge badge-on">Ativo</span>
              : <span className="badge badge-off">Desativado</span>}
          </td>
          <td className="actions">
            <button className="btn btn-ghost btn-sm">Editar</button>
            <button className="btn btn-danger-ghost btn-sm">Excluir</button>
          </td>
        </tr>
      ))}
    </>
  );
}

function SearchTotalPerson(){
  const [pessoas, setPessoas] = useState<Person[]>([]);
  const [erro, setErro] = useState<string | null>(null);
  
  useEffect(() => {
    fetch('/api/person')
      .then((resposta) => {
        if (!resposta.ok) throw new Error(`HTTP ${resposta.status}`);
        return resposta.json();
      })
      .then((dados: Person[]) => setPessoas(dados))
      .catch((e) => setErro(String(e)));
  }, []);

  if (erro) {
    console.log("Erro ao completar requisição!");
  }

  return pessoas;
}
