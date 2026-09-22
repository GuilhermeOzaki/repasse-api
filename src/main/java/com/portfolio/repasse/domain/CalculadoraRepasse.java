package com.portfolio.repasse.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculadoraRepasse {

    public BigDecimal calcularRepasse(SolicitacaoRepasse solicitacao) {
        BigDecimal percentualComissao = switch (solicitacao.modalidadeEntrega()) {
            case ENTREGA_IFOOD -> new BigDecimal("0.27");
            case ENTREGA_PROPRIA -> new BigDecimal("0.12");
        };

        BigDecimal percentualPagamento = switch (solicitacao.formaPagamento()) {
            case PIX -> new BigDecimal("0.0099");
            case CREDITO -> new BigDecimal("0.032");
            case DEBITO -> new BigDecimal("0.020");
        };

        return solicitacao.valorPedido()
                .subtract(solicitacao.valorPedido().multiply(percentualComissao).setScale(2, RoundingMode.HALF_UP))
                .subtract(solicitacao.valorPedido().multiply(percentualPagamento).setScale(2, RoundingMode.HALF_UP))
                .subtract(solicitacao.valorCupom().multiply(new BigDecimal("0.50")).setScale(2, RoundingMode.HALF_UP));

    }


}
