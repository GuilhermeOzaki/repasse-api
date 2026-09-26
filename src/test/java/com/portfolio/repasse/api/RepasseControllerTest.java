package com.portfolio.repasse.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RepasseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void postValidoRetornaValorDoRepasse() throws Exception {
        mockMvc.perform(post("/repasses")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {"valorPedido": 100, "modalidadeEntrega": "ENTREGA_IFOOD", "formaPagamento": "CREDITO"}
                """)).andExpect(status().isOk())
                .andExpect(jsonPath("$.valorRepasse").value(69.80));
    }

    @Test
    void cupomMaiorQuePedidoRetornaBadRequest() throws Exception {
        mockMvc.perform(post("/repasses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {"valorPedido": 10, "modalidadeEntrega": "ENTREGA_IFOOD", "formaPagamento": "CREDITO", "valorCupom": 30}
                """)).andExpect(status().isBadRequest());
    }

    @Test
    void getRetornaRepassesSalvos() throws Exception {
        mockMvc.perform(post("/repasses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {"valorPedido": 100, "modalidadeEntrega": "ENTREGA_IFOOD", "formaPagamento": "CREDITO"}
                """));
        mockMvc.perform(get("/repasses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].valorRepasse").value(69.80));
    }
}
