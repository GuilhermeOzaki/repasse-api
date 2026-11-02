import { useEffect, useState } from 'react'
import { buscarHistorico } from './api.js'
import FormularioRepasse from './components/FormularioRepasse.jsx'
import Resultado from './components/Resultado.jsx'
import Historico from './components/Historico.jsx'

export default function App() {
  const [resultado, setResultado] = useState(null)
  const [historico, setHistorico] = useState([])
  const [carregandoHistorico, setCarregandoHistorico] = useState(true)
  const [erroHistorico, setErroHistorico] = useState('')

  async function carregarHistorico() {
    try {
      const lista = await buscarHistorico()
      setHistorico([...lista].reverse()) // mais recente primeiro
      setErroHistorico('')
    } catch (erro) {
      setErroHistorico(erro.message)
    } finally {
      setCarregandoHistorico(false)
    }
  }

  useEffect(() => {
    carregarHistorico()
  }, [])

  function aoCalcular(novoResultado) {
    setResultado(novoResultado)
    carregarHistorico()
  }

  return (
    <div className="pagina">
      <header className="topo">
        <h1>Calculadora de Repasse</h1>
        <p>
          Num pedido de R$ 100 no iFood, o restaurante não recebe R$ 100.
          Descubra quanto sobra depois da comissão, da taxa e do cupom.
        </p>
      </header>

      <main className="principal">
        <FormularioRepasse aoCalcular={aoCalcular} />
        <Resultado resultado={resultado} />
      </main>

      <Historico
        registros={historico}
        carregando={carregandoHistorico}
        erro={erroHistorico}
      />

      <footer className="rodape">
        <a href="/swagger-ui.html">Documentação da API (Swagger)</a>
        <a href="https://github.com/GuilhermeOzaki/repasse-api">Código no GitHub</a>
      </footer>
    </div>
  )
}
