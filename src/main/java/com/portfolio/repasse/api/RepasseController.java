package com.portfolio.repasse.api;

import com.portfolio.repasse.domain.CalculadoraRepasse;
import com.portfolio.repasse.domain.SolicitacaoRepasse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/repasses")
public class RepasseController {
    private final CalculadoraRepasse calculadora;

    public RepasseController(CalculadoraRepasse calculadora) {
        this.calculadora = calculadora;
    }

    @PostMapping
    public RepasseResponse calcular(@RequestBody RepasseRequest request){
        SolicitacaoRepasse solicitacao = request.toSolicitacao();
        return new RepasseResponse(calculadora.calcularRepasse(solicitacao));
    }
}
