// Modal de confirmação de exclusão — apenas visual.

export function ConfirmDialog() {
  return (
    <div className="overlay">
      <div className="dialog" role="alertdialog" aria-modal="true" aria-labelledby="confirm-title">
        <h3 id="confirm-title">Excluir pessoa?</h3>
        <p>
          <strong>Ana Souza</strong> e todos os endereços vinculados serão removidos. Esta ação não pode ser desfeita.
        </p>
        <div className="dialog-actions">
          <button className="btn btn-ghost">Cancelar</button>
          <button className="btn btn-danger">Excluir</button>
        </div>
      </div>
    </div>
  );
}
