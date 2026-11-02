import { formatarReais, MODALIDADES, PAGAMENTOS } from '../formatar.js'

export default function Historico({ registros, carregando, erro }) {
  return (
    <section className="historico" aria-labelledby="titulo-historico">
      <h2 id="titulo-historico">Cálculos anteriores</h2>

      {carregando && <p className="historico-msg">Carregando histórico…</p>}

      {!carregando && erro && <p className="historico-msg erro">{erro}</p>}

      {!carregando && !erro && registros.length === 0 && (
        <p className="historico-msg">
          Nenhum cálculo ainda. Faça o primeiro e ele aparece aqui.
        </p>
      )}

      {registros.length > 0 && (
        <div className="tabela-rolagem">
          <table>
            <thead>
              <tr>
                <th scope="col">Pedido</th>
                <th scope="col">Cupom</th>
                <th scope="col">Entrega</th>
                <th scope="col">Pagamento</th>
                <th scope="col" className="num">Repasse</th>
              </tr>
            </thead>
            <tbody>
              {registros.map((r) => (
                <tr key={r.id}>
                  <td>{formatarReais(r.valorPedido)}</td>
                  <td>{Number(r.valorCupom) > 0 ? formatarReais(r.valorCupom) : '—'}</td>
                  <td>{MODALIDADES[r.modalidadeEntrega] ?? r.modalidadeEntrega}</td>
                  <td>{PAGAMENTOS[r.formaPagamento] ?? r.formaPagamento}</td>
                  <td className="num destaque">{formatarReais(r.valorRepasse)}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </section>
  )
}
