package com.portfolio.repasse.domain;

import java.math.BigDecimal;

public record SolicitacaoRepasse(
        BigDecimal valorPedido,
        ModalidadeEntrega modalidadeEntrega,
        FormaPagamento formaPagamento,
        BigDecimal valorCupom)
{

    public SolicitacaoRepasse {
        if(valorCupom.compareTo(valorPedido) > 0){
            throw new IllegalArgumentException("Valor do cupom inválido");
        }
    }

}
