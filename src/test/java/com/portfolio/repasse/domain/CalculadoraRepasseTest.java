package com.portfolio.repasse.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class CalculadoraRepasseTest {
    @Test
    void repasseComEntregaIfoodCredito() {
        SolicitacaoRepasse solicitacao = new SolicitacaoRepasse(new BigDecimal("100")
                , ModalidadeEntrega.ENTREGA_IFOOD
                , FormaPagamento.CREDITO
                , BigDecimal.ZERO);

        BigDecimal resultado = new CalculadoraRepasse().calcularRepasse(solicitacao);
        assertThat(resultado).isEqualByComparingTo("69.80");
    }

    @Test
    void repasseComEntregaPropriaPix() {
        SolicitacaoRepasse solicitacao = new SolicitacaoRepasse(new BigDecimal("100")
                , ModalidadeEntrega.ENTREGA_PROPRIA
                , FormaPagamento.PIX
                , BigDecimal.ZERO);

        BigDecimal resultado = new CalculadoraRepasse().calcularRepasse(solicitacao);
        assertThat(resultado).isEqualByComparingTo("87.01");
    }

    @Test
    void repasseComEntregaIfoodCreditoCupom() {
        SolicitacaoRepasse solicitacao = new SolicitacaoRepasse(new BigDecimal("100")
                , ModalidadeEntrega.ENTREGA_IFOOD
                , FormaPagamento.CREDITO
                , new BigDecimal("20"));

        BigDecimal resultado = new CalculadoraRepasse().calcularRepasse(solicitacao);
        assertThat(resultado).isEqualByComparingTo("59.80");
    }
}
