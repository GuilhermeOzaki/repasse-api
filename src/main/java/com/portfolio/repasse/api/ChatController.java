package com.portfolio.repasse.api;

import com.portfolio.repasse.agente.RepasseTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder builder, RepasseTools repasseTools) {
        this.chatClient = builder
                .defaultSystem("""
                        Você é um assistente que tira dúvidas de donos de restaurante sobre o repasse do iFood.
                        Responda sempre em português, de forma curta.
                        Nunca faça contas sozinho: para calcular um repasse, use sempre a ferramenta calcularRepasse.
                        Se faltar alguma informação (valor, entrega, pagamento), pergunte antes de calcular.
                        Se não houver cupom, use 0.
                        """)
                .defaultTools(repasseTools)
                .build();
    }

    @PostMapping
    public String conversar(@RequestBody String pergunta) {
        return chatClient.prompt()
                .user(pergunta)
                .call()
                .content();
    }
}