package com.portfolio.repasse.historico;

import com.portfolio.repasse.domain.FormaPagamento;
import com.portfolio.repasse.domain.ModalidadeEntrega;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class RegistroRepasse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal valorPedido;
    private BigDecimal valorCupom;
    private BigDecimal valorRepasse;
    @Enumerated(EnumType.STRING)
    private ModalidadeEntrega modalidadeEntrega;
    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;

    protected RegistroRepasse() {
    }

    public RegistroRepasse(BigDecimal valorPedido, BigDecimal valorCupom, BigDecimal valorRepasse, ModalidadeEntrega modalidadeEntrega, FormaPagamento formaPagamento) {
        this.valorPedido = valorPedido;
        this.valorCupom = valorCupom;
        this.valorRepasse = valorRepasse;
        this.modalidadeEntrega = modalidadeEntrega;
        this.formaPagamento = formaPagamento;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getValorPedido() {
        return valorPedido;
    }

    public BigDecimal getValorCupom() {
        return valorCupom;
    }

    public BigDecimal getValorRepasse() {
        return valorRepasse;
    }

    public ModalidadeEntrega getModalidadeEntrega() {
        return modalidadeEntrega;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }
}
