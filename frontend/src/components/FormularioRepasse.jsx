import { useEffect, useState } from 'react'
import { calcularRepasse } from '../api.js'
import { MODALIDADES, PAGAMENTOS } from '../formatar.js'

function GrupoOpcoes({ legenda, nome, opcoes, valor, aoMudar }) {
  return (
    <fieldset className="grupo">
      <legend>{legenda}</legend>
      <div className="opcoes">
        {Object.entries(opcoes).map(([chave, rotulo]) => (
          <label key={chave} className="opcao">
            <input
              type="radio"
              name={nome}
              value={chave}
              checked={valor === chave}
              onChange={() => aoMudar(chave)}
            />
            <span>{rotulo}</span>
          </label>
        ))}
      </div>
    </fieldset>
  )
}

export default function FormularioRepasse({ aoCalcular }) {
  const [valorPedido, setValorPedido] = useState('')
  const [valorCupom, setValorCupom] = useState('')
  const [modalidadeEntrega, setModalidadeEntrega] = useState('ENTREGA_IFOOD')
  const [formaPagamento, setFormaPagamento] = useState('PIX')
  const [erro, setErro] = useState('')
  const [enviando, setEnviando] = useState(false)
  const [demorando, setDemorando] = useState(false)

  // O plano grátis do Render "dorme": avisa se a resposta demorar
  useEffect(() => {
    if (!enviando) {
      setDemorando(false)
      return
    }
    const timer = setTimeout(() => setDemorando(true), 3000)
    return () => clearTimeout(timer)
  }, [enviando])

  async function enviar(evento) {
    evento.preventDefault()
    setErro('')

    const pedido = Number(valorPedido)
    if (!valorPedido || pedido <= 0) {
      setErro('Informe o valor do pedido, maior que zero.')
      return
    }

    const dados = {
      valorPedido: pedido,
      modalidadeEntrega,
      formaPagamento,
      valorCupom: valorCupom ? Number(valorCupom) : 0,
    }

    setEnviando(true)
    try {
      const resposta = await calcularRepasse(dados)
      aoCalcular({ ...dados, valorRepasse: resposta.valorRepasse })
    } catch (e) {
      setErro(e.message)
    } finally {
      setEnviando(false)
    }
  }

  return (
    <form className="formulario" onSubmit={enviar} noValidate>
      <div className="campo">
        <label htmlFor="valorPedido">Valor do pedido</label>
        <div className="entrada-reais">
          <span aria-hidden="true">R$</span>
          <input
            id="valorPedido"
            type="number"
            inputMode="decimal"
            step="0.01"
            min="0"
            placeholder="100,00"
            value={valorPedido}
            onChange={(e) => setValorPedido(e.target.value)}
          />
        </div>
      </div>

      <GrupoOpcoes
        legenda="Quem entrega"
        nome="modalidadeEntrega"
        opcoes={MODALIDADES}
        valor={modalidadeEntrega}
        aoMudar={setModalidadeEntrega}
      />

      <GrupoOpcoes
        legenda="Forma de pagamento"
        nome="formaPagamento"
        opcoes={PAGAMENTOS}
        valor={formaPagamento}
        aoMudar={setFormaPagamento}
      />

      <div className="campo">
        <label htmlFor="valorCupom">
          Cupom <small>(opcional)</small>
        </label>
        <div className="entrada-reais">
          <span aria-hidden="true">R$</span>
          <input
            id="valorCupom"
            type="number"
            inputMode="decimal"
            step="0.01"
            min="0"
            placeholder="0,00"
            value={valorCupom}
            onChange={(e) => setValorCupom(e.target.value)}
          />
        </div>
      </div>

      {erro && (
        <p className="erro" role="alert">
          {erro}
        </p>
      )}

      <button type="submit" disabled={enviando}>
        {enviando ? 'Calculando…' : 'Calcular repasse'}
      </button>

      {demorando && (
        <p className="aviso" role="status">
          O servidor estava dormindo. A primeira resposta pode levar uns 50 segundos.
        </p>
      )}
    </form>
  )
}
