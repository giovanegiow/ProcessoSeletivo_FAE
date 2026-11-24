import type { Address } from '../components/AddressFields';

// Formato que a API devolve em GET /person (cada pessoa já vem completa)
export type Person = {
  id: number;
  name: string;
  phoneNumber: string;
  emailAddress: string;
  status: 'ACTIVE' | 'DISABLE';
  type: 'PERSON' | 'STUDENT' | 'PROFESSOR';
  addresses: Address[];
  studentNumber?: number | null; // só existe em aluno
  photo?: string | null;         // só existe em aluno (imagem em base64)
  salary?: number | null;        // só existe em professor
};
