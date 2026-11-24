// Formulário de cadastro de aluno ou professor.

import { useState } from 'react';
import { AddressFields, emptyAddress, type Address } from './AddressFields';

type PersonType = 'STUDENT' | 'PROFESSOR';
type Status = 'ACTIVE' | 'DISABLE';

export function PersonForm() {
  const [type, setType] = useState<PersonType>('STUDENT');
  const [name, setName] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [emailAddress, setEmailAddress] = useState("");
  const [status, setStatus] = useState<Status>("ACTIVE");
  const [studentNumber, setStudentNumber] = useState("");
  const [salary, setSalary] = useState("");
  // Uma LISTA de endereços, começando com um endereço vazio (o mínimo é 1)
  const [addresses, setAddresses] = useState<Address[]>([emptyAddress()]);
  const [mensagem, setMensagem] = useState<string | null>(null);

  async function RegisterPerson(e: React.FormEvent) {
    e.preventDefault(); // impede o navegador de recarregar a página

    // Campos comuns + o campo específico de cada tipo
    const body = {
      name,
      phoneNumber,
      emailAddress,
      status,
      addresses, // o backend espera "addresses" (lista), não "address"
      // Campo vazio vai como null (Number("") daria 0 e passaria na validação)
      ...(type === 'STUDENT'
        ? { studentNumber: studentNumber === '' ? null : Number(studentNumber) }
        : { salary: salary === '' ? null : Number(salary) }),
    };

    const response = await fetch(type === 'STUDENT' ? '/api/student' : '/api/professor', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body), // sem chaves em volta do body
    });

    if (!response.ok) {
      // O backend devolve { "campo": "mensagem" } quando a validação falha
      const erros = await response.json();
      setMensagem('Erro ao cadastrar: ' + Object.values(erros).join(', '));
      return;
    }

    setMensagem('Cadastrado com sucesso!');
  }

  return (
    <form className="card form" noValidate onSubmit={RegisterPerson}>
      <header className="form-header">
        <h2>Nova pessoa</h2>
      </header>

      <fieldset className="section">
        <legend>Tipo</legend>
        <div className="segmented" role="radiogroup">
          <button type="button" role="radio" aria-checked={type === 'STUDENT'}
            className={type === 'STUDENT' ? 'active' : ''}
            onClick={() => setType('STUDENT')}>Aluno</button>
          <button type="button" role="radio" aria-checked={type === 'PROFESSOR'}
            className={type === 'PROFESSOR' ? 'active' : ''}
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

      {/* O estado fica aqui no PersonForm; o AddressFields só mostra e avisa mudanças */}
      <AddressFields addresses={addresses} onChange={setAddresses} />

      {mensagem && <p className="muted" style={{ padding: '0 20px' }}>{mensagem}</p>}

      <footer className="form-footer">
        <button type="button" className="btn btn-ghost">Cancelar</button>
        <button type="submit" className="btn btn-primary">Cadastrar</button>
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
