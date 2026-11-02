// Chamadas para a API do Spring Boot (mesmo endereço do front)

export async function calcularRepasse(dados) {
  const resposta = await fetch('/repasses', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(dados),
  })

  if (!resposta.ok) {
    // O TratadorDeErros devolve a mensagem em texto (ex.: "Valor do cupom inválido")
    const mensagem = await resposta.text()
    throw new Error(mensagem || 'Não foi possível calcular. Confira os valores.')
  }

  return resposta.json()
}

export async function buscarHistorico() {
  const resposta = await fetch('/repasses')
  if (!resposta.ok) {
    throw new Error('Não foi possível carregar o histórico.')
  }
  return resposta.json()
}
