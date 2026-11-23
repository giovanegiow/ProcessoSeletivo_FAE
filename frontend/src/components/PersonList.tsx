// Tela de listagem — apenas visual. Os dados abaixo são placeholders estáticos.

export function PersonList() {
  return (
    <section className="card">
      <div className="toolbar">
        <div className="tabs" role="tablist">
          <button role="tab" className="active">
            Todos <span className="count">3</span>
          </button>
          <button role="tab">
            Alunos <span className="count">2</span>
          </button>
          <button role="tab">
            Professores <span className="count">1</span>
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
            <tr>
              <td data-label="Nome">
                <div className="person-cell">
                  <div className="avatar"><span>AS</span></div>
                  <span>Ana Souza</span>
                </div>
              </td>
              <td data-label="Tipo"><span className="tag tag-student">Aluno</span></td>
              <td data-label="Contato">
                <div>
                  <div>ana.souza@email.com</div>
                  <div className="muted">(41) 99999-1234</div>
                </div>
              </td>
              <td data-label="Detalhe">Matrícula 2024001</td>
              <td data-label="Cidade">Curitiba/PR</td>
              <td data-label="Status"><span className="badge badge-on">Ativo</span></td>
              <td className="actions">
                <button className="btn btn-ghost btn-sm">Editar</button>
                <button className="btn btn-danger-ghost btn-sm">Excluir</button>
              </td>
            </tr>
            <tr>
              <td data-label="Nome">
                <div className="person-cell">
                  <div className="avatar"><span>BL</span></div>
                  <span>Bruno Lima</span>
                </div>
              </td>
              <td data-label="Tipo"><span className="tag tag-student">Aluno</span></td>
              <td data-label="Contato">
                <div>
                  <div>bruno.lima@email.com</div>
                  <div className="muted">(41) 98888-5678</div>
                </div>
              </td>
              <td data-label="Detalhe">Matrícula 2024002</td>
              <td data-label="Cidade">
                <span>São José dos Pinhais/PR <span className="muted">+1</span></span>
              </td>
              <td data-label="Status"><span className="badge badge-off">Desativado</span></td>
              <td className="actions">
                <button className="btn btn-ghost btn-sm">Editar</button>
                <button className="btn btn-danger-ghost btn-sm">Excluir</button>
              </td>
            </tr>
            <tr>
              <td data-label="Nome">
                <div className="person-cell">
                  <div className="avatar"><span>CM</span></div>
                  <span>Carla Mendes</span>
                </div>
              </td>
              <td data-label="Tipo"><span className="tag tag-professor">Professor</span></td>
              <td data-label="Contato">
                <div>
                  <div>carla.mendes@email.com</div>
                  <div className="muted">(41) 3333-4444</div>
                </div>
              </td>
              <td data-label="Detalhe">R$ 8.500,00</td>
              <td data-label="Cidade">Curitiba/PR</td>
              <td data-label="Status"><span className="badge badge-on">Ativo</span></td>
              <td className="actions">
                <button className="btn btn-ghost btn-sm">Editar</button>
                <button className="btn btn-danger-ghost btn-sm">Excluir</button>
              </td>
            </tr>
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
