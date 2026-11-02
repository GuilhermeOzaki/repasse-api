import { formatarReais, MODALIDADES, PAGAMENTOS } from '../formatar.js'

export default function Resultado({ resultado }) {
  if (!resultado) {
    return (
      <section className="resultado vazio" aria-live="polite">
        <p className="resultado-titulo">O restaurante recebe</p>
        <p className="resultado-valor">R$ —</p>
        <p className="resultado-dica">
          Preencha o pedido ao lado e veja aqui quanto fica para o restaurante.
        </p>
      </section>
    )
  }

  const { valorPedido, valorRepasse, modalidadeEntrega, formaPagamento } = resultado
  const descontos = valorPedido - valorRepasse
  const porcentagem = Math.max(0, Math.min(100, (valorRepasse / valorPedido) * 100))

  return (
    <section className="resultado" aria-live="polite">
      <p className="resultado-titulo">O restaurante recebe</p>
      <p className="resultado-valor" key={valorRepasse}>
        {formatarReais(valorRepasse)}
      </p>

      <div
        className="barra"
        role="img"
        aria-label={`Fica com o restaurante ${porcentagem.toFixed(0)}% do pedido`}
      >
        <div className="barra-repasse" style={{ width: `${porcentagem}%` }} />
      </div>

      <dl className="resumo">
        <div>
          <dt>Pedido</dt>
          <dd>{formatarReais(valorPedido)}</dd>
        </div>
        <div>
          <dt>Descontos</dt>
          <dd className="negativo">− {formatarReais(descontos)}</dd>
        </div>
        <div>
          <dt>Fica com o restaurante</dt>
          <dd>{porcentagem.toFixed(1).replace('.', ',')}%</dd>
        </div>
      </dl>

      <p className="resultado-dica">
        {MODALIDADES[modalidadeEntrega]}, pago no {PAGAMENTOS[formaPagamento].toLowerCase()}.
      </p>
    </section>
  )
}
