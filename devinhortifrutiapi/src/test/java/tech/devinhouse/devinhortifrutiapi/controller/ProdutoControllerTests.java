package tech.devinhouse.devinhortifrutiapi.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import tech.devinhouse.devinhortifrutiapi.dto.ProdutoDTO;
import tech.devinhouse.devinhortifrutiapi.model.Produto;
import tech.devinhouse.devinhortifrutiapi.repository.ProdutoRepository;
import tech.devinhouse.devinhortifrutiapi.service.ProdutoService;

import javax.persistence.EntityExistsException;
import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@SpringBootTest
@AutoConfigureMockMvc
public class ProdutoControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProdutoService produtoService;

    @MockBean
    private ProdutoRepository produtoRepository;

    @BeforeEach
    public void initDB() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Batata");
        produto.setDescricao("Descrição");
        produto.setUrlFoto("www.url.com");
        produto.setPrecoSugerido(BigDecimal.valueOf(1.90));
        produto.setIsAtivo(true);
        produtoRepository.save(produto);
    }

    @Test
    @DisplayName("Salvar Produto com Usuario ADM")
    public void salvarProdutoUsuarioADM() throws Exception {
        String body = "{\"login\":\"admin2\",\"senha\":\"(d4NO8^ie#\"}";

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/login")
                        .header("Content-Type", "application/json" )
                        .content(body))
                .andExpect(status().isOk()).andReturn();

        String response = result.getResponse().getContentAsString();
        JSONObject json = new JSONObject(response);
        String token = (String) json.get("token");

        Assertions.assertNotNull(token);

        when(produtoService.adicionaProduto(any(ProdutoDTO.class))).thenReturn(1L);

        String bodyRequisicao = "{\n" +
                "    \"nome\":\"Produto\",\n" +
                "    \"descricao\":\"Descrição Produto\",\n" +
                "    \"urlFoto\":\"https://www.google.com\",\n" +
                "    \"precoSugerido\":1.90,\n" +
                "    \"isAtivo\":true\n" +
                "}";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/produto")
                        .header("Authorization", "Bearer " + token)
                        .header("Content-Type", "application/json" )
                        .content(bodyRequisicao)

                )
                .andExpect(status().isCreated())
                .andReturn();

        String responsePost = mvcResult.getResponse().getContentAsString();
        Assertions.assertNotEquals(mvcResult, "");
        Assertions.assertEquals("1", responsePost);
    }

    @Test
    @DisplayName("Não salvar Produto Usuario não Autenticado")
    public void naoSalvarProdutoUsuarioNaoAutenticado() throws Exception {
        String body = "{\"login\":\"outroLogin\",\"senha\":\"outraSenha\"}";

        mockMvc
                .perform(MockMvcRequestBuilders.post("/produto")
                        .header("Content-Type", "application/json")
                        .content(body))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Não salvar Produto Nome inválido")
    public void naoSalvarProdutoNomeInvalido() throws Exception {
        String body = "{\"login\":\"admin2\",\"senha\":\"(d4NO8^ie#\"}";

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/login")
                        .header("Content-Type", "application/json")
                        .content(body))
                .andExpect(status().isOk()).andReturn();

        String response = result.getResponse().getContentAsString();
        JSONObject json = new JSONObject(response);
        String token = (String) json.get("token");

        Assertions.assertNotNull(token);

        String bodyRequisicao = "{\n" +
                "    \"nome\":\"\",\n" +
                "    \"descricao\":\"Descrição Produto\",\n" +
                "    \"urlFoto\":\"https://www.google.com\",\n" +
                "    \"precoSugerido\":1.90,\n" +
                "    \"isAtivo\":true\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/produto")
                        .header("Authorization", "Bearer " + token)
                        .header("Content-Type", "application/json")
                        .content(bodyRequisicao)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Não salvar Produto Descrição inválida")
    public void naoSalvarProdutoDescricaoInvalida() throws Exception {
        String body = "{\"login\":\"admin2\",\"senha\":\"(d4NO8^ie#\"}";

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/login")
                        .header("Content-Type", "application/json")
                        .content(body))
                .andExpect(status().isOk()).andReturn();

        String response = result.getResponse().getContentAsString();
        JSONObject json = new JSONObject(response);
        String token = (String) json.get("token");

        Assertions.assertNotNull(token);

        String bodyRequisicao = "{\n" +
                "    \"nome\":\"Produto\",\n" +
                "    \"descricao\":\"\",\n" +
                "    \"urlFoto\":\"https://www.google.com\",\n" +
                "    \"precoSugerido\":1.90,\n" +
                "    \"isAtivo\":true\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/produto")
                        .header("Authorization", "Bearer " + token)
                        .header("Content-Type", "application/json")
                        .content(bodyRequisicao)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Não salvar Produto URL inválida")
    public void naoSalvarProdutoURLInvalida() throws Exception {
        String body = "{\"login\":\"admin2\",\"senha\":\"(d4NO8^ie#\"}";

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/login")
                        .header("Content-Type", "application/json")
                        .content(body))
                .andExpect(status().isOk()).andReturn();

        String response = result.getResponse().getContentAsString();
        JSONObject json = new JSONObject(response);
        String token = (String) json.get("token");

        Assertions.assertNotNull(token);

        String bodyRequisicao = "{\n" +
                "    \"nome\":\"Produto\",\n" +
                "    \"descricao\":\"Descrição Produto\",\n" +
                "    \"urlFoto\":,\n" +
                "    \"precoSugerido\":1.90,\n" +
                "    \"isAtivo\":true\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/produto")
                        .header("Authorization", "Bearer " + token)
                        .header("Content-Type", "application/json")
                        .content(bodyRequisicao)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Não salvar Produto Preço inválido")
    public void naoSalvarProdutoPrecoInvalido() throws Exception {
        String body = "{\"login\":\"admin2\",\"senha\":\"(d4NO8^ie#\"}";

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/login")
                        .header("Content-Type", "application/json")
                        .content(body))
                .andExpect(status().isOk()).andReturn();

        String response = result.getResponse().getContentAsString();
        JSONObject json = new JSONObject(response);
        String token = (String) json.get("token");

        Assertions.assertNotNull(token);

        String bodyRequisicao = "{\n" +
                "    \"nome\":\"Produto\",\n" +
                "    \"descricao\":\"Descrição Produto\",\n" +
                "    \"urlFoto\":\"https://www.google.com\",\n" +
                "    \"precoSugerido\":,\n" +
                "    \"isAtivo\":true\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/produto")
                        .header("Authorization", "Bearer " + token)
                        .header("Content-Type", "application/json")
                        .content(bodyRequisicao)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Não salvar Produto Preço zerado")
    public void naoSalvarProdutoPrecoZerado() throws Exception {
        String body = "{\"login\":\"admin2\",\"senha\":\"(d4NO8^ie#\"}";

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/login")
                        .header("Content-Type", "application/json")
                        .content(body))
                .andExpect(status().isOk()).andReturn();

        String response = result.getResponse().getContentAsString();
        JSONObject json = new JSONObject(response);
        String token = (String) json.get("token");

        Assertions.assertNotNull(token);

        when(produtoService.adicionaProduto(any(ProdutoDTO.class))).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));

        String bodyRequisicao = "{\n" +
                "    \"nome\":\"Produto\",\n" +
                "    \"descricao\":\"Descrição Produto\",\n" +
                "    \"urlFoto\":\"https://www.google.com\",\n" +
                "    \"precoSugerido\":0.00,\n" +
                "    \"isAtivo\":true\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/produto")
                        .header("Authorization", "Bearer " + token)
                        .header("Content-Type", "application/json")
                        .content(bodyRequisicao)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Não salvar Produto Preço negativo")
    public void naoSalvarProdutoPrecoNegativo() throws Exception {
        String body = "{\"login\":\"admin2\",\"senha\":\"(d4NO8^ie#\"}";

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/login")
                        .header("Content-Type", "application/json")
                        .content(body))
                .andExpect(status().isOk()).andReturn();

        String response = result.getResponse().getContentAsString();
        JSONObject json = new JSONObject(response);
        String token = (String) json.get("token");

        Assertions.assertNotNull(token);

        when(produtoService.adicionaProduto(any(ProdutoDTO.class))).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));

        String bodyRequisicao = "{\n" +
                "    \"nome\":\"Produto\",\n" +
                "    \"descricao\":\"Descrição Produto\",\n" +
                "    \"urlFoto\":\"https://www.google.com\",\n" +
                "    \"precoSugerido\":-1.90,\n" +
                "    \"isAtivo\":true\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/produto")
                        .header("Authorization", "Bearer " + token)
                        .header("Content-Type", "application/json")
                        .content(bodyRequisicao)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Não salvar Produto inativo")
    public void naoSalvarProdutoPrecoInativo() throws Exception {
        String body = "{\"login\":\"admin2\",\"senha\":\"(d4NO8^ie#\"}";

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/login")
                        .header("Content-Type", "application/json")
                        .content(body))
                .andExpect(status().isOk()).andReturn();

        String response = result.getResponse().getContentAsString();
        JSONObject json = new JSONObject(response);
        String token = (String) json.get("token");

        Assertions.assertNotNull(token);

        when(produtoService.adicionaProduto(any(ProdutoDTO.class))).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));

        String bodyRequisicao = "{\n" +
                "    \"nome\":\"Produto\",\n" +
                "    \"descricao\":\"Descrição Produto\",\n" +
                "    \"urlFoto\":\"https://www.google.com\",\n" +
                "    \"precoSugerido\":1.90,\n" +
                "    \"isAtivo\":false\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/produto")
                        .header("Authorization", "Bearer " + token)
                        .header("Content-Type", "application/json")
                        .content(bodyRequisicao)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Não salvar Produto com Nome existente")
    public void naoSalvarProdutoNomeExistente() throws Exception {
        String body = "{\"login\":\"admin2\",\"senha\":\"(d4NO8^ie#\"}";

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/login")
                        .header("Content-Type", "application/json")
                        .content(body))
                .andExpect(status().isOk()).andReturn();

        String response = result.getResponse().getContentAsString();
        JSONObject json = new JSONObject(response);
        String token = (String) json.get("token");

        Assertions.assertNotNull(token);

        Produto produto1 = new Produto();
        produto1.setId(1L);
        produto1.setNome("Batata");
        produto1.setDescricao("Decrição Batata");
        produto1.setUrlFoto("www.urlbatata.com");
        produto1.setPrecoSugerido(BigDecimal.valueOf(1.90));
        produto1.setIsAtivo(true);


        Produto produto2 = new Produto();
        produto2.setId(2L);
        produto2.setNome("Batata");
        produto2.setDescricao("Qualquer Descrição");
        produto2.setUrlFoto("Qualquer URL");
        produto2.setPrecoSugerido(BigDecimal.valueOf(1.90));
        produto2.setIsAtivo(true);
        when(produtoService.adicionaProduto(any(ProdutoDTO.class))).thenThrow(new EntityExistsException());

        String bodyRequisicao = "{\n" +
                "    \"nome\":\"Batata\",\n" +
                "    \"descricao\":\"Qualquer Descrição\",\n" +
                "    \"urlFoto\":\"Qualquer URL\",\n" +
                "    \"precoSugerido\":1.90,\n" +
                "    \"isAtivo\":true\n" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/produto")
                        .header("Authorization", "Bearer " + token)
                        .header("Content-Type", "application/json" )
                        .content(bodyRequisicao)
                )
                .andExpect(status().isConflict());
    }

}
