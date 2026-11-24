// Formulário de cadastro/edição — apenas visual.
// Mostra os campos de Person + Student e de Professor; o comportamento
// (alternar tipo, salvar, validar) fica por sua conta.

import { useState } from 'react';
import { AddressFields } from './AddressFields';

interface Person {
  name: string;
  phoneNumber: string;
  emailAddress: string;
  // address: Address;
  type: PersonType;
  status: Status;
}

interface Address {
  street: string;
  city: string;
  state: string;
  zipCode: string;
  country: string;
}

type PersonType = 'STUDENT' | 'PROFESSOR';
type Status = 'ACTIVE' | 'DISABLE';

const [name, setName] = useState("");
const [phoneNumber, setPhoneNumber] = useState("");
const [emailAddress, setEmailAddress] = useState("");
const [status, setStatus] = useState<Status>("ACTIVE");
const [street, setStreet] = useState("");
const [city, setCity] = useState("");
const [state, setState] = useState("");
const [zipCode, setZipCode] = useState("");
const [country, setCountry] = useState("");
const [type, setType] = useState<PersonType>('STUDENT');

export function PersonForm() {
  return (
    <form className="card form" noValidate>
      <header className="form-header">
        <h2>Nova pessoa</h2>
      </header>

      <fieldset className="section">
        <legend>Tipo</legend>
        <div className="segmented" role="radiogroup">
          <button type="button" role="radio" aria-checked={type === 'STUDENT'} className={type === 'STUDENT' ? 'active' : ''} onClick={() => setType('STUDENT')}>Aluno</button>
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
            <input name="phoneNumber" value={phoneNumber} inputMode="tel" placeholder="(00) 00000-0000" onChange={(e) => setPhoneNumber(e.target.value)} />
          </label>
          <label>
            E-mail (emailAddress)
            <input name="emailAddress" value={emailAddress} type="email" aria-invalid="true" onChange={(e) => setEmailAddress(e.target.value)} />
            {/* <small className="error">Exemplo de mensagem de erro</small> */}
          </label>
          <label>
            Status
            <select name="status" defaultValue="ACTIVE" value={status} onChange={(e) => setStatus(e.target.value as Status)}>
              <option value="ACTIVE">Ativo</option>
              <option value="DISABLE">Desativado</option>
            </select>
          </label>
        </div>
      </fieldset>

      {type === 'STUDENT' ? <ShowStudent /> : <ShowProfessor />}

      <AddressFields />

      <footer className="form-footer">
        <button type="button" className="btn btn-ghost">Cancelar</button>
        <button type="submit" className="btn btn-primary" onClick={RegisterPerson}>Cadastrar</button>
      </footer>
    </form>
  );
}

function ShowStudent() {
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
          <input name="studentNumber" />
        </label>
      </div>
    </fieldset>
  )
}

function ShowProfessor() {
  return (
    <fieldset className="section">
      <legend>Dados do professor</legend>
      <div className="grid">
        <label>
          Salário (salary)
          <input name="salary" type="number" min={0} step="0.01" placeholder="0,00" />
        </label>
      </div>
    </fieldset>
  )
}

async function RegisterPerson() {
  const body: Person = {
    name: name,
    emailAddress: emailAddress,
    phoneNumber: phoneNumber,
    status: status,
    type: type
  }

  const response = await fetch('/api/person', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ body }),
  });

  if (!response.ok) {
    throw new Error(`Erro HTTP: ${response.status}`);
  }
}