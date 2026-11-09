package com.portfolio.repasse.agente;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class AvaliacaoAgente {

    public static void main(String[] args) throws Exception {
        List<String> linhas = Files.readAllLines(
                Path.of("src/test/resources/avaliacao.csv"), StandardCharsets.UTF_8);
        HttpClient client = HttpClient.newHttpClient();

        int acertos = 0;
        int total = 0;

        for (String linha : linhas.subList(1, linhas.size())) {
            String[] partes = linha.split(";");
            String pergunta = partes[0];
            String esperado = partes[1];

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/chat"))
                    .header("Content-Type", "text/plain; charset=utf-8")
                    .POST(HttpRequest.BodyPublishers.ofString(pergunta, StandardCharsets.UTF_8))
                    .build();
            String resposta = client.send(request,
                    HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8)).body();

            boolean acertou;
            if (esperado.equals("PERGUNTA")) {
                acertou = resposta.contains("?") && !resposta.contains("R$");
            } else {
                acertou = resposta.contains(esperado);
            }

            total++;
            if (acertou) acertos++;
            System.out.println((acertou ? "[OK]   " : "[ERRO] ") + pergunta);
            if (!acertou) System.out.println("       esperado: " + esperado + " | resposta: " + resposta);

            Thread.sleep(8000);
        }

        System.out.printf("%nAcertos: %d de %d (%.0f%%)%n", acertos, total, acertos * 100.0 / total);
    }
}