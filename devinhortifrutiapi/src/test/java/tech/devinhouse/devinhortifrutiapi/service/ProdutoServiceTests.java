package tech.devinhouse.devinhortifrutiapi.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import tech.devinhouse.devinhortifrutiapi.dto.ProdutoDTO;
import tech.devinhouse.devinhortifrutiapi.model.Produto;
import tech.devinhouse.devinhortifrutiapi.repository.ProdutoRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ProdutoServiceTests {
    private static final Long ID = 1L;
    private static final String NOME = "Teste Nome Produto";
    private static final String DESCRICAO = "Descrição Teste Produto";
    private static final String URL = "www.testeproduto.com";
    private static final BigDecimal PRECO = BigDecimal.valueOf(1.99);
    private static final Boolean ATIVO = Boolean.valueOf(true);

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;
    private ProdutoDTO produtoDTO;
    private Produto produto;
    private Optional<Produto> optionalProduto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        startProduto();
    }

    private void startProduto() {
        produtoDTO = new ProdutoDTO();
        produtoDTO.setNome(NOME);
        produtoDTO.setDescricao(DESCRICAO);
        produtoDTO.setUrlFoto(URL);
        produtoDTO.setPrecoSugerido(PRECO);
        produtoDTO.setIsAtivo(ATIVO);
        produto = new Produto(ID, NOME, DESCRICAO, URL, PRECO, ATIVO);
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
}
