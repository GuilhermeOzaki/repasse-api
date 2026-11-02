export function formatarReais(valor) {
  return Number(valor).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })
}

export const MODALIDADES = {
  ENTREGA_IFOOD: 'Entrega iFood',
  ENTREGA_PROPRIA: 'Entrega própria',
}

export const PAGAMENTOS = {
  PIX: 'Pix',
  CREDITO: 'Crédito',
  DEBITO: 'Débito',
}
