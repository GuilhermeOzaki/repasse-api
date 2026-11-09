package com.portfolio.repasse.agente;

import com.portfolio.repasse.domain.CalculadoraRepasse;
import com.portfolio.repasse.domain.FormaPagamento;
import com.portfolio.repasse.domain.ModalidadeEntrega;
import com.portfolio.repasse.domain.SolicitacaoRepasse;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class RepasseTools {

    private final CalculadoraRepasse calculadora;

    public RepasseTools(CalculadoraRepasse calculadora) {
        this.calculadora = calculadora;
    }

    @Tool(description = "Calcula o valor líquido que o restaurante recebe (repasse) de um pedido do iFood, já descontando comissão, taxa de pagamento e cupom")
    public BigDecimal calcularRepasse(
            @ToolParam(description = "Valor total do pedido em reais") BigDecimal valorPedido,
            @ToolParam(description = "Quem faz a entrega: ENTREGA_IFOOD ou ENTREGA_PROPRIA") ModalidadeEntrega modalidadeEntrega,
            @ToolParam(description = "Forma de pagamento: PIX, CREDITO ou DEBITO") FormaPagamento formaPagamento,
            @ToolParam(description = "Valor do cupom de desconto em reais; use 0 se não houver cupom") BigDecimal valorCupom) {
        return calculadora.calcularRepasse(
                new SolicitacaoRepasse(valorPedido, modalidadeEntrega, formaPagamento, valorCupom));
    }
}