package com.portfolio.repasse.domain;

import java.math.BigDecimal;

public record SolicitacaoRepasse(
        BigDecimal valorPedido,
        ModalidadeEntrega modalidadeEntrega,
        FormaPagamento formaPagamento,
        BigDecimal valorCupom


) {

}
