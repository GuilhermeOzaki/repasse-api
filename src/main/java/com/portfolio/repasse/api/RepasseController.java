package com.portfolio.repasse.api;

import com.portfolio.repasse.domain.CalculadoraRepasse;
import com.portfolio.repasse.domain.SolicitacaoRepasse;
import com.portfolio.repasse.historico.RegistroRepasse;
import com.portfolio.repasse.historico.RegistroRepasseRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/repasses")
public class RepasseController {
    private final CalculadoraRepasse calculadora;
    private final RegistroRepasseRepository repository;


    public RepasseController(CalculadoraRepasse calculadora, RegistroRepasseRepository repository) {
        this.calculadora = calculadora;
        this.repository = repository;
    }

    @PostMapping
    public RepasseResponse calcular(@RequestBody RepasseRequest request){
        SolicitacaoRepasse solicitacao = request.toSolicitacao();
        BigDecimal valorRepasse = calculadora.calcularRepasse(solicitacao);
        repository.save(new RegistroRepasse(solicitacao.valorPedido(),
                solicitacao.valorCupom(),
                valorRepasse,
                solicitacao.modalidadeEntrega(),
                solicitacao.formaPagamento()));
        return new RepasseResponse(valorRepasse);
    }
}
