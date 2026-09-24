package com.portfolio.repasse.api;

import com.portfolio.repasse.domain.FormaPagamento;
import com.portfolio.repasse.domain.ModalidadeEntrega;
import com.portfolio.repasse.domain.SolicitacaoRepasse;

import java.math.BigDecimal;

public record RepasseRequest(BigDecimal valorPedido,
                             ModalidadeEntrega modalidadeEntrega,
                             FormaPagamento formaPagamento,
                             BigDecimal valorCupom)
{

    public SolicitacaoRepasse toSolicitacao(){
        if(valorCupom == null)
            return new SolicitacaoRepasse(valorPedido,modalidadeEntrega,formaPagamento,BigDecimal.ZERO);
        else
            return new SolicitacaoRepasse(valorPedido,modalidadeEntrega,formaPagamento,valorCupom);
    }

}
