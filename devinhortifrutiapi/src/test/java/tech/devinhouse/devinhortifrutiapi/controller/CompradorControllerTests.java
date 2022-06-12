package tech.devinhouse.devinhortifrutiapi.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@SpringBootTest
@AutoConfigureMockMvc
public class CompradorControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Usuario não admin busca comprador por id")
    public void nao_deve_listar_comprador_quando_nao_for_admin() throws Exception {
        //gera token
        String body = new JSONObject()
                .put("login", "visitante2")
                .put("senha", "k#4UMcH2s&")
                .toString();

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/login")
                        .header("Content-Type", "application/json")
                        .content(body))
                .andExpect(status().isOk()).andReturn();

        //extrai token
        String response = result.getResponse().getContentAsString();
        JSONObject json = new JSONObject(response);
        String token = (String) json.get("token");
        Assertions.assertNotNull(token);

        //executando controller com o token
        mockMvc.perform(MockMvcRequestBuilders.get("/comprador")
                        .header("Authorization", "Bearer " + token)
                        .header("Content-Type", "application/json")
                        .param("cpf", "12345678900")
                )
                .andExpect(status().isForbidden());
    }

}
