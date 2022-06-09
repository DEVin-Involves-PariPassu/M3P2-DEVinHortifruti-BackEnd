package tech.devinhouse.devinhortifrutiapi.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;
import tech.devinhouse.devinhortifrutiapi.dto.ProdutoDTO;
import tech.devinhouse.devinhortifrutiapi.model.Produto;
import tech.devinhouse.devinhortifrutiapi.repository.ProdutoRepository;
import tech.devinhouse.devinhortifrutiapi.service.exception.RequiredFieldMissingException;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProdutoServiceTests {
    private static final Long ID = 1L;
    private static final String NOME = "TESTANDO";
    private static final String DESCRICAO = "Descrição Teste Produto";
    private static final String URL = "www.testeproduto.com";
    private static final BigDecimal PRECO = BigDecimal.valueOf(1.99);
    private static final Boolean ATIVO = true;

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService = new ProdutoService();

    private ProdutoDTO produtoDTO;
    private Optional<Produto> optionalProduto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        startProduto();
    }

    private void startProduto() {
        produtoDTO = new ProdutoDTO();
        produtoDTO.setId(ID);
        produtoDTO.setNome(NOME);
        produtoDTO.setDescricao(DESCRICAO);
        produtoDTO.setUrlFoto(URL);
        produtoDTO.setPrecoSugerido(PRECO);
        produtoDTO.setIsAtivo(ATIVO);
        Produto produto = new Produto(ID, NOME, DESCRICAO, URL, PRECO, ATIVO);
        optionalProduto = Optional.of(new Produto(ID, NOME, DESCRICAO, URL, PRECO, ATIVO));
        this.produtoRepository.save(produto);
        produtoService.adicionaProduto(produtoDTO);
    }

    @Test
    @DisplayName("Deve listar produto por Id")
    public void deveListarProdutoPorId() {
        when(produtoRepository.findById(Mockito.anyLong())).thenReturn(optionalProduto);

        Produto response = produtoService.listarPorId(ID);

        assertNotNull(response);
        assertEquals(Produto.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NOME, response.getNome());
        assertEquals(DESCRICAO, response.getDescricao());
        assertEquals(URL, response.getUrlFoto());
        assertEquals(PRECO, response.getPrecoSugerido());
        Assertions.assertEquals(ATIVO, response.isAtivo());
    }

    @Test
    @DisplayName("Produto não encontrado")
    public void produtoNaoEncontrado() {
        when(produtoRepository.findById(anyLong())).thenThrow(new EntityNotFoundException("Não há produto com este Id."));

        try {
            produtoService.listarPorId(ID);
        } catch (Exception e) {
            assertEquals(EntityNotFoundException.class, e.getClass());
            assertEquals("Não há produto com este Id.", e.getMessage());
        }
    }

    @Test
    @DisplayName("Salvar produto com corpo requisição válido")
    public void salvarProdutoComCorpoValido() {
        when(produtoService.adicionaProduto(produtoDTO)).thenReturn(anyLong());

        Long response = produtoService.adicionaProduto(produtoDTO);

        assertNotNull(response);
        assertEquals(Long.class, response.getClass());
    }

    @Test
    @DisplayName("Não salvar produto com Nome inválido")
    public void naoSalvarProdutoNomeInvalido() {
        produtoDTO.setNome("");

        assertThrows(ResponseStatusException.class, () -> produtoService.adicionaProduto(produtoDTO));
    }

    @Test
    @DisplayName("Não salvar produto com Descrição inválida")
    public void naoSalvarProdutoDescricaoInvalida() {
        produtoDTO.setDescricao("");

        assertThrows(ResponseStatusException.class, () -> produtoService.adicionaProduto(produtoDTO));
    }

    @Test
    @DisplayName("Não salvar produto com URL inválida")
    public void naoSalvarProdutoURLInvalida() {
        produtoDTO.setUrlFoto("");

        assertThrows(ResponseStatusException.class, () -> produtoService.adicionaProduto(produtoDTO));
    }

    @Test
    @DisplayName("Não salvar produto com Preço inválido")
    public void naoSalvarProdutoPrecoNulo() {
        produtoDTO.setPrecoSugerido(null);

        assertThrows(ResponseStatusException.class, () -> produtoService.adicionaProduto(produtoDTO));
    }

    @Test
    @DisplayName("Não salvar produto com Preço negativo")
    public void naoSalvarProdutoPrecoNegativo() {
        produtoDTO.setPrecoSugerido(BigDecimal.valueOf(-1));

        assertThrows(ResponseStatusException.class, () -> produtoService.adicionaProduto(produtoDTO));
    }

}
