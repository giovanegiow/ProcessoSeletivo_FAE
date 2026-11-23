// Bloco de endereços (Person 1 -> 1..* Address) — apenas visual.

function AddressCard({ index }: { index: number }) {
  return (
    <div className="address-card">
      <div className="address-head">
        <strong>Endereço {index}</strong>
        <button type="button" className="btn btn-ghost btn-sm">Remover</button>
      </div>
      <div className="grid">
        <label className="col-span-2">
          Rua (street)
          <input name="street" />
        </label>
        <label>
          CEP (zipCode)
          <input name="zipCode" inputMode="numeric" placeholder="00000-000" />
        </label>
        <label>
          Cidade (city)
          <input name="city" />
        </label>
        <label>
          Estado (state)
          <input name="state" />
        </label>
        <label>
          País (country)
          <input name="country" defaultValue="Brasil" />
        </label>
      </div>
    </div>
  );
}

export function AddressFields() {
  return (
    <fieldset className="section">
      <legend>
        Endereços <span className="muted">(mínimo 1)</span>
      </legend>
      <AddressCard index={1} />
      <button type="button" className="btn btn-secondary">+ Adicionar endereço</button>
    </fieldset>
  );
}
