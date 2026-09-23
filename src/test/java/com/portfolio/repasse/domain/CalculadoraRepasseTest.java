package com.portfolio.repasse.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CalculadoraRepasseTest {
    @Test
    void descontaComissaoIfoodETaxaDeCredito() {
        SolicitacaoRepasse solicitacao = new SolicitacaoRepasse(new BigDecimal("100")
                , ModalidadeEntrega.ENTREGA_IFOOD
                , FormaPagamento.CREDITO
                , BigDecimal.ZERO);

        BigDecimal resultado = new CalculadoraRepasse().calcularRepasse(solicitacao);
        assertThat(resultado).isEqualByComparingTo("69.80");
    }

    @Test
    void descontaComissaoPropriaETaxaDePix() {
        SolicitacaoRepasse solicitacao = new SolicitacaoRepasse(new BigDecimal("100")
                , ModalidadeEntrega.ENTREGA_PROPRIA
                , FormaPagamento.PIX
                , BigDecimal.ZERO);

        BigDecimal resultado = new CalculadoraRepasse().calcularRepasse(solicitacao);
        assertThat(resultado).isEqualByComparingTo("87.01");
    }

    @Test
    void descontaMetadeDoCupom() {
        SolicitacaoRepasse solicitacao = new SolicitacaoRepasse(new BigDecimal("100")
                , ModalidadeEntrega.ENTREGA_IFOOD
                , FormaPagamento.CREDITO
                , new BigDecimal("20"));

        BigDecimal resultado = new CalculadoraRepasse().calcularRepasse(solicitacao);
        assertThat(resultado).isEqualByComparingTo("59.80");
    }

    @Test
    void arredondaComissaoETaxaParaDuasCasas() {
        SolicitacaoRepasse solicitacao = new SolicitacaoRepasse(new BigDecimal("87.40")
                , ModalidadeEntrega.ENTREGA_IFOOD
                , FormaPagamento.CREDITO
                , BigDecimal.ZERO);

        BigDecimal resultado = new CalculadoraRepasse().calcularRepasse(solicitacao);
        assertThat(resultado).isEqualByComparingTo("61.00");
    }

    @Test
    void arredondaParticipacaoDoCupom() {
        SolicitacaoRepasse solicitacao = new SolicitacaoRepasse(new BigDecimal("100")
                , ModalidadeEntrega.ENTREGA_IFOOD
                , FormaPagamento.CREDITO
                , new BigDecimal("15.55"));

        BigDecimal resultado = new CalculadoraRepasse().calcularRepasse(solicitacao);
        assertThat(resultado).isEqualByComparingTo("62.02");
    }

    @Test
    void recusaCupomMaiorQueOPedido(){
        assertThatThrownBy(() -> new SolicitacaoRepasse(BigDecimal.TEN
                , ModalidadeEntrega
                .ENTREGA_IFOOD
                , FormaPagamento.CREDITO
                , new BigDecimal("35")))
        .isInstanceOf(IllegalArgumentException.class);
    }



}
