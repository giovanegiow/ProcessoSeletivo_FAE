// Formulário de cadastro e edição de aluno ou professor.
// Sem a prop "pessoa": cadastra (POST). Com a prop "pessoa": edita (PUT).

import { useState } from 'react';
import { AddressFields, emptyAddress, type Address } from './AddressFields';
import type { Person } from '../types/Person';

type TypePerson = 'STUDENT' | 'PROFESSOR';
type Status = 'ACTIVE' | 'DISABLE';

interface PersonFormProps {
  pessoa?: Person | null;   // pessoa que está sendo editada (opcional)
  onCancel?: () => void;    // avisa o App que a edição foi cancelada
}

export function PersonForm({ pessoa, onCancel }: PersonFormProps) {
  const editando = !!pessoa;   // true se recebeu uma pessoa

  // Os valores iniciais vêm da pessoa (edição) ou ficam vazios (cadastro)
  const [type, setType] = useState<TypePerson>(pessoa?.type === 'PROFESSOR' ? 'PROFESSOR' : 'STUDENT');
  const [name, setName] = useState(pessoa?.name ?? "");
  const [phoneNumber, setPhoneNumber] = useState(pessoa?.phoneNumber ?? "");
  const [emailAddress, setEmailAddress] = useState(pessoa?.emailAddress ?? "");
  const [status, setStatus] = useState<Status>(pessoa?.status ?? "ACTIVE");
  const [studentNumber, setStudentNumber] = useState(pessoa?.studentNumber?.toString() ?? "");
  const [salary, setSalary] = useState(pessoa?.salary?.toString() ?? "");
  const [addresses, setAddresses] = useState<Address[]>(
    pessoa && pessoa.addresses.length > 0 ? pessoa.addresses : [emptyAddress()]
  );
  const [mensagem, setMensagem] = useState<string | null>(null);

  async function SavePerson(e: React.FormEvent) {
    e.preventDefault();

    const body = {
      name,
      phoneNumber,
      emailAddress,
      status,
      addresses,
      ...(type === 'STUDENT'
        ? { studentNumber: studentNumber === '' ? null : Number(studentNumber) }
        : { salary: salary === '' ? null : Number(salary) }),
    };

    const base = type === 'STUDENT' ? '/api/student' : '/api/professor';

    // Cadastro: POST /api/student        Edição: PUT /api/student/5
    const response = await fetch(editando ? `${base}/${pessoa.id}` : base, {
      method: editando ? 'PUT' : 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body),
    });

    if (!response.ok) {
      const erro = await response.json();
      // 400 devolve { campo: mensagem }; 404 devolve { message: "..." }
      setMensagem('Erro ao salvar: ' + (erro.message ?? Object.values(erro).join(', ')));
      return;
    }

    window.location.reload(); // recarrega para a listagem mostrar a mudança
  }

  return (
    <form className="card form" noValidate onSubmit={SavePerson}>
      <header className="form-header">
        <h2>{editando ? `Editar: ${pessoa.name}` : 'Nova pessoa'}</h2>
      </header>

      <fieldset className="section">
        <legend>Tipo</legend>
        <div className="segmented" role="radiogroup">
          {/* Na edição o tipo fica travado: um aluno não vira professor */}
          <button type="button" role="radio" aria-checked={type === 'STUDENT'}
            className={type === 'STUDENT' ? 'active' : ''}
            disabled={editando && type !== 'STUDENT'}
            onClick={() => setType('STUDENT')}>Aluno</button>
          <button type="button" role="radio" aria-checked={type === 'PROFESSOR'}
            className={type === 'PROFESSOR' ? 'active' : ''}
            disabled={editando && type !== 'PROFESSOR'}
            onClick={() => setType('PROFESSOR')}>Professor</button>
        </div>
      </fieldset>

      <fieldset className="section">
        <legend>Dados pessoais</legend>
        <div className="grid">
          <label className="col-span-2">
            Nome (name)
            <input name="name" value={name} onChange={(e) => setName(e.target.value)} />
          </label>
          <label>
            Telefone (phoneNumber)
            <input name="phoneNumber" value={phoneNumber} inputMode="tel" placeholder="(00) 00000-0000"
              onChange={(e) => setPhoneNumber(e.target.value)} />
          </label>
          <label>
            E-mail (emailAddress)
            <input name="emailAddress" value={emailAddress} type="email"
              onChange={(e) => setEmailAddress(e.target.value)} />
          </label>
          <label>
            Status
            <select name="status" value={status} onChange={(e) => setStatus(e.target.value as Status)}>
              <option value="ACTIVE">Ativo</option>
              <option value="DISABLE">Desativado</option>
            </select>
          </label>
        </div>
      </fieldset>

      {type === 'STUDENT'
        ? <ShowStudent studentNumber={studentNumber} onChange={setStudentNumber} />
        : <ShowProfessor salary={salary} onChange={setSalary} />}

      <AddressFields addresses={addresses} onChange={setAddresses} />

      {mensagem && <p className="muted" style={{ padding: '0 20px' }}>{mensagem}</p>}

      <footer className="form-footer">
        <button type="button" className="btn btn-ghost" onClick={onCancel}>Cancelar</button>
        <button type="submit" className="btn btn-primary">
          {editando ? 'Salvar alterações' : 'Cadastrar'}
        </button>
      </footer>
    </form>
  );
}

function ShowStudent({ studentNumber, onChange }: { studentNumber: string; onChange: (v: string) => void }) {
  return (
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
          <input name="studentNumber" inputMode="numeric" value={studentNumber}
            onChange={(e) => onChange(e.target.value)} />
        </label>
      </div>
    </fieldset>
  );
}

function ShowProfessor({ salary, onChange }: { salary: string; onChange: (v: string) => void }) {
  return (
    <fieldset className="section">
      <legend>Dados do professor</legend>
      <div className="grid">
        <label>
          Salário (salary)
          <input name="salary" type="number" min={0} step="0.01" placeholder="0,00" value={salary}
            onChange={(e) => onChange(e.target.value)} />
        </label>
      </div>
    </fieldset>
  );
}
