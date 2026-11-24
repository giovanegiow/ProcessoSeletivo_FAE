// Tela de listagem — apenas visual. Os dados abaixo são placeholders estáticos.
import { useEffect, useState } from 'react';
import { EditarPessoa } from './PersonForm';

type Person = {
  id: number;
  name: string;
  phoneNumber: string;
  emailAddress: string;
  status: 'ACTIVE' | 'DISABLE';
  type: TypePerson
};

type TypePerson = 'PERSON' | 'STUDENT' | 'PROFESSOR';

export function PersonList() {
  const numPerson = SearchTotalPerson();

  const numStudents = numPerson.filter((p) => p.type === 'STUDENT').length;
  const numProfessors = numPerson.filter((p) => p.type === 'PROFESSOR').length;

  const [typeSearch, setTypeSearch] = useState<TypePerson>("PERSON");

  return (
    <section className="card">
      <div className="toolbar">
        <div className="tabs" role="tablist">
          <button role="tab" className={typeSearch === "PERSON" ? 'active' : ""} onClick={() => setTypeSearch('PERSON')}>
            Todos <span className="count">{numPerson.length}</span>
          </button>
          <button role="tab" className={typeSearch === "STUDENT" ? 'active' : ""} onClick={() => setTypeSearch('STUDENT')}>
            Alunos <span className="count">{numStudents}</span>
          </button>
          <button role="tab" className={typeSearch === "PROFESSOR" ? 'active' : ""} onClick={() => setTypeSearch('PROFESSOR')}>
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
            {typeSearch === "PERSON" ? <SearchPerson /> : typeSearch === "STUDENT" ? <SearchStudents /> : <SearchProfessor />}
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
          <td data-label="Tipo">{pessoa.type == "STUDENT" ? "Estudante" : "Professor"}</td>
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
            <button className="btn btn-ghost btn-sm" onClick={() => CarregarDadosPessoa(pessoa.id)}>Editar</button>
            <button className="btn btn-danger-ghost btn-sm">Excluir</button>
          </td>
        </tr>
      ))}
    </>
  );
}

function SearchStudents() {
  const [estudantes, setEstudante] = useState<Person[]>([]);
  const [erro, setErro] = useState<string | null>(null);

  useEffect(() => {
    fetch('/api/student')
      .then((resposta) => {
        if (!resposta.ok) throw new Error(`HTTP ${resposta.status}`);
        return resposta.json();
      })
      .then((dados: Person[]) => setEstudante(dados))
      .catch((e) => setErro(String(e)));
  }, []);

  if (erro) {
    return (
      <tr>
        <td colSpan={7} className="error">Erro ao buscar estudantes: {erro}</td>
      </tr>
    );
  }

  if (estudantes.length === 0) {
    return (
      <tr>
        <td colSpan={7} className="empty">Nenhum estudante cadastrado ainda.</td>
      </tr>
    );
  }

  return (
    <>
      {estudantes.map((estudante) => (
        <tr key={estudante.id}>
          <td data-label="Nome">
            <div className="person-cell">
              <div className="avatar"><span>{iniciais(estudante.name)}</span></div>
              <span>{estudante.name}</span>
            </div>
          </td>
          <td data-label="Tipo">Estudante</td>
          <td data-label="Contato">
            <div>
              <div>{estudante.emailAddress}</div>
              <div className="muted">{estudante.phoneNumber}</div>
            </div>
          </td>
          <td data-label="Detalhe">—</td>
          <td data-label="Cidade">—</td>
          <td data-label="Status">
            {estudante.status === 'ACTIVE'
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

function SearchProfessor() {
  const [professores, setProfessor] = useState<Person[]>([]);
  const [erro, setErro] = useState<string | null>(null);

  useEffect(() => {
    fetch('/api/professor')
      .then((resposta) => {
        if (!resposta.ok) throw new Error(`HTTP ${resposta.status}`);
        return resposta.json();
      })
      .then((dados: Person[]) => setProfessor(dados))
      .catch((e) => setErro(String(e)));
  }, []);

  if (erro) {
    return (
      <tr>
        <td colSpan={7} className="error">Erro ao buscar estudantes: {erro}</td>
      </tr>
    );
  }

  if (professores.length === 0) {
    return (
      <tr>
        <td colSpan={7} className="empty">Nenhum estudante cadastrado ainda.</td>
      </tr>
    );
  }

  return (
    <>
      {professores.map((professor) => (
        <tr key={professor.id}>
          <td data-label="Nome">
            <div className="person-cell">
              <div className="avatar"><span>{iniciais(professor.name)}</span></div>
              <span>{professor.name}</span>
            </div>
          </td>
          <td data-label="Tipo">Professor</td>
          <td data-label="Contato">
            <div>
              <div>{professor.emailAddress}</div>
              <div className="muted">{professor.phoneNumber}</div>
            </div>
          </td>
          <td data-label="Detalhe">—</td>
          <td data-label="Cidade">—</td>
          <td data-label="Status">
            {professor.status === 'ACTIVE'
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

function SearchTotalPerson() {
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

function CarregarDadosPessoa(id: number){
  const [pessoas, setPessoas] = useState<Person[]>([]);
  const [erro, setErro] = useState<string | null>(null);

  useEffect(() => {
    fetch(`/api/person/${id}`)
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
        <td colSpan={7} className="error">Erro ao buscar pessoa: {erro}</td>
      </tr>
    );
  }

  if (pessoas.length === 0) {
    return (
      <tr>
        <td colSpan={7} className="empty">Nenhuma pessoa localizada.</td>
      </tr>
    );
  }

  EditarPessoa(pessoas[0]);
}