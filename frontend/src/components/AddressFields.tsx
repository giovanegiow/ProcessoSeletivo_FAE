// Bloco de endereços (Person 1 -> 1..* Address).
// Este componente NÃO guarda estado: ele recebe a lista de endereços do PersonForm
// (props) e avisa o PersonForm quando algo muda (onChange).

export interface Address {
  street: string;
  city: string;
  state: string;
  zipCode: string;
  country: string;
}

export function emptyAddress(): Address {
  return { street: '', city: '', state: '', zipCode: '', country: 'Brasil' };
}

interface AddressFieldsProps {
  addresses: Address[];
  onChange: (addresses: Address[]) => void;
}

export function AddressFields({ addresses, onChange }: AddressFieldsProps) {
  // Atualiza UM campo de UM endereço e devolve a lista nova para o PersonForm
  function updateField(index: number, field: keyof Address, value: string) {
    const newList = addresses.map((address, i) =>
      i === index ? { ...address, [field]: value } : address
    );
    onChange(newList);
  }

  function addAddress() {
    onChange([...addresses, emptyAddress()]);
  }

  function removeAddress(index: number) {
    onChange(addresses.filter((_, i) => i !== index));
  }

  return (
    <fieldset className="section">
      <legend>
        Endereços <span className="muted">(mínimo 1)</span>
      </legend>

      {addresses.map((address, index) => (
        <div className="address-card" key={index}>
          <div className="address-head">
            <strong>Endereço {index + 1}</strong>
            <button
              type="button"
              className="btn btn-ghost btn-sm"
              disabled={addresses.length === 1}
              onClick={() => removeAddress(index)}
            >
              Remover
            </button>
          </div>
          <div className="grid">
            <label className="col-span-2">
              Rua (street)
              <input value={address.street} onChange={(e) => updateField(index, 'street', e.target.value)} />
            </label>
            <label>
              CEP (zipCode)
              <input
                value={address.zipCode}
                inputMode="numeric"
                placeholder="00000-000"
                onChange={(e) => updateField(index, 'zipCode', e.target.value)}
              />
            </label>
            <label>
              Cidade (city)
              <input value={address.city} onChange={(e) => updateField(index, 'city', e.target.value)} />
            </label>
            <label>
              Estado (state)
              <input value={address.state} onChange={(e) => updateField(index, 'state', e.target.value)} />
            </label>
            <label>
              País (country)
              <input value={address.country} onChange={(e) => updateField(index, 'country', e.target.value)} />
            </label>
          </div>
        </div>
      ))}

      <button type="button" className="btn btn-secondary" onClick={addAddress}>
        + Adicionar endereço
      </button>
    </fieldset>
  );
}
