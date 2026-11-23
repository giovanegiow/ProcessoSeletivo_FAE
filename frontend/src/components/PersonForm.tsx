// Formulário de cadastro/edição — apenas visual.
// Mostra os campos de Person + Student e de Professor; o comportamento
// (alternar tipo, salvar, validar) fica por sua conta.

import { AddressFields } from './AddressFields';

export function PersonForm() {
  return (
    <form className="card form" noValidate>
      <header className="form-header">
        <h2>Nova pessoa</h2>
      </header>

      <fieldset className="section">
        <legend>Tipo</legend>
        <div className="segmented" role="radiogroup">
          <button type="button" role="radio" aria-checked="true" className="active">Aluno</button>
          <button type="button" role="radio" aria-checked="false">Professor</button>
        </div>
      </fieldset>

      <fieldset className="section">
        <legend>Dados pessoais</legend>
        <div className="grid">
          <label className="col-span-2">
            Nome (name)
            <input name="name" />
          </label>
          <label>
            Telefone (phoneNumber)
            <input name="phoneNumber" inputMode="tel" placeholder="(00) 00000-0000" />
          </label>
          <label>
            E-mail (emailAddress)
            <input name="emailAddress" type="email" aria-invalid="true" />
            <small className="error">Exemplo de mensagem de erro</small>
          </label>
          <label>
            Status
            <select name="status" defaultValue="ACTIVE">
              <option value="ACTIVE">Ativo</option>
              <option value="DISABLE">Desativado</option>
            </select>
          </label>
        </div>
      </fieldset>

      {/* Student */}
      <fieldset className="section">
        <legend>Dados do aluno</legend>
        <div className="student-grid">
          <div className="photo-picker">
            <div className="avatar avatar-lg"><span>?</span></div>
            <label className="btn btn-secondary btn-sm file-btn">
              Enviar foto
              <input type="file" name="photo" accept="image/*" />
            </label>
          </div>
          <label>
            Matrícula (studentNumber)
            <input name="studentNumber" />
          </label>
        </div>
      </fieldset>

      {/* Professor — renderize no lugar do bloco acima quando o tipo for Professor */}
      <fieldset className="section">
        <legend>Dados do professor</legend>
        <div className="grid">
          <label>
            Salário (salary)
            <input name="salary" type="number" min={0} step="0.01" placeholder="0,00" />
          </label>
        </div>
      </fieldset>

      <AddressFields />

      <footer className="form-footer">
        <button type="button" className="btn btn-ghost">Cancelar</button>
        <button type="submit" className="btn btn-primary">Cadastrar</button>
      </footer>
    </form>
  );
}
