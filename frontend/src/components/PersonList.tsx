// Tela de listagem: busca as pessoas na API uma vez e filtra pela aba escolhida.
import { useEffect, useState } from 'react';
import type { Person } from '../types/Person';

type TypePerson = 'PERSON' | 'STUDENT' | 'PROFESSOR';

interface PersonListProps {
  onEdit: (pessoa: Person) => void; // avisa o App qual pessoa editar
}

export function PersonList({ onEdit }: PersonListProps) {
  const { pessoas, erro } = SearchTotalPerson(); // um fetch só, para tudo
  const [typeSearch, setTypeSearch] = useState<TypePerson>("PERSON");

  const numStudents = pessoas.filter((p) => p.type === 'STUDENT').length;
  const numProfessors = pessoas.filter((p) => p.type === 'PROFESSOR').length;

  // O que aparece na tabela depende da aba: todos, ou só um tipo
  const visiveis = typeSearch === 'PERSON'
    ? pessoas
    : pessoas.filter((p) => p.type === typeSearch);

  return (
    <section className="card">
      <div className="toolbar">
        <div className="tabs" role="tablist">
          <button role="tab" className={typeSearch === "PERSON" ? 'active' : ""} onClick={() => setTypeSearch('PERSON')}>
            Todos <span className="count">{pessoas.length}</span>
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
            <PersonRows pessoas={visiveis} erro={erro} onEdit={onEdit} />
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

const dinheiro = new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' });

interface PersonRowsProps {
  pessoas: Person[];
  erro: string | null;
  onEdit: (pessoa: Person) => void;
}

// Só desenha as linhas: não busca nada, recebe a lista pronta.
function PersonRows({ pessoas, erro, onEdit }: PersonRowsProps) {
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
        <td colSpan={7} className="empty">Nenhuma pessoa encontrada.</td>
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
          <td data-label="Tipo">{pessoa.type === "STUDENT" ? "Estudante" : "Professor"}</td>
          <td data-label="Contato">
            <div>
              <div>{pessoa.emailAddress}</div>
              <div className="muted">{pessoa.phoneNumber}</div>
            </div>
          </td>
          <td data-label="Detalhe">
            {pessoa.type === 'STUDENT'
              ? `Matrícula ${pessoa.studentNumber}`
              : pessoa.salary != null ? dinheiro.format(pessoa.salary) : '—'}
          </td>
          <td data-label="Cidade">
            {pessoa.addresses.length > 0 ? `${pessoa.addresses[0].city}/${pessoa.addresses[0].state}` : '—'}
          </td>
          <td data-label="Status">
            {pessoa.status === 'ACTIVE'
              ? <span className="badge badge-on">Ativo</span>
              : <span className="badge badge-off">Desativado</span>}
          </td>
          <td className="actions">
            {/* A lista já tem os dados completos: é só entregar a pessoa ao App */}
            <button className="btn btn-ghost btn-sm" onClick={() => onEdit(pessoa)}>Editar</button>
            <button className="btn btn-danger-ghost btn-sm">Excluir</button>
          </td>
        </tr>
      ))}
    </>
  );
}

// Busca todas as pessoas uma vez. Usa hooks, então só pode ser chamada no topo
// de um componente (é o que o PersonList faz).
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

  return { pessoas, erro };
}
