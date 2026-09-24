package com.portfolio.repasse.domain;

import com.portfolio.repasse.config.ComissaoProperties;
import com.portfolio.repasse.config.PagamentoProperties;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculadoraRepasse {

    private final ComissaoProperties comissaoProperties;
    private final PagamentoProperties pagamentoProperties;

    public CalculadoraRepasse(ComissaoProperties comissaoProperties, PagamentoProperties pagamentoProperties) {
        this.comissaoProperties = comissaoProperties;
        this.pagamentoProperties = pagamentoProperties;
    }

    public BigDecimal calcularRepasse(SolicitacaoRepasse solicitacao) {
        BigDecimal percentualComissao = switch (solicitacao.modalidadeEntrega()) {
            case ENTREGA_IFOOD -> comissaoProperties.entregaIfood();
            case ENTREGA_PROPRIA -> comissaoProperties.entregaPropria();
        };

        BigDecimal percentualPagamento = switch (solicitacao.formaPagamento()) {
            case PIX -> pagamentoProperties.pix();
            case CREDITO -> pagamentoProperties.credito();
            case DEBITO -> pagamentoProperties.debito();
        };

        return solicitacao.valorPedido()
                .subtract(solicitacao.valorPedido().multiply(percentualComissao).setScale(2, RoundingMode.HALF_UP))
                .subtract(solicitacao.valorPedido().multiply(percentualPagamento).setScale(2, RoundingMode.HALF_UP))
                .subtract(solicitacao.valorCupom().multiply(new BigDecimal("0.50")).setScale(2, RoundingMode.HALF_UP));

    }


}
