package tech.devinhouse.devinhortifrutiapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import tech.devinhouse.devinhortifrutiapi.dto.ItemVendaGetDto;
import tech.devinhouse.devinhortifrutiapi.dto.ItemVendaPostDto;
import tech.devinhouse.devinhortifrutiapi.dto.VendaGetDto;
import tech.devinhouse.devinhortifrutiapi.dto.VendaPostDto;
import tech.devinhouse.devinhortifrutiapi.model.*;
import tech.devinhouse.devinhortifrutiapi.repository.ItemVendaRepository;
import tech.devinhouse.devinhortifrutiapi.repository.ProdutoRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ItemVendaServiceTests {

    @Autowired
    ItemVendaRepository itemVendaRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @InjectMocks
    private ItemVendaService itemVendaService = new ItemVendaService();

    private static final String NOME_CLIENTE = "Ana";
    private static final String TELEFONE = "+5548912345678";
    private static final BigDecimal TOTAL_VENDA = BigDecimal.valueOf(399.90);
    private static final String ENDERECO = "Rua Comprador, 10";

    private static final Long ID_COMPRADOR = 1L;
    private static final Long ID_VENDEDOR = 1L;
    private static final String EMAIL_COMPRADOR = "comprador@gmail.com";
    private static final String SIGLA_ESTADO = "PR";
    private static final String CEP = "83607056";
    private static final String CIDADE = "Curitiba";
    private static final String LOGRADOURO = "Rua tal, 56";
    private static final String BAIRRO = "Vila Elizabeth";
    private static final String COMPLEMENTO = "Rua sem saida";
    private static final String DATA_ENTREGA = "12/10/2022";

    private static final String NOME_VENDEDOR = "Brenda";
    private static final String EMAIL_VENDEDOR = "vendedor@gmail.com";
    private static final String LOGIN_VENDEDOR = "brenda";
    private static final String NASCIMENTO = "11/10/1996";
    private static final String CPF = "12345678910";

    private static final Long ID_VENDA = 1L;

    private Usuario usuario;
    private Comprador comprador;
    private Venda novaVenda;
    private ItemVenda novoItemVenda;
    private List<ItemVenda> itemVenda;
    private VendaGetDto vendaGet;
    private ItemVendaGetDto itemVendaGetDto;
    private List<ItemVendaGetDto> itemVendaGetLista;
    private VendaPostDto vendaPost;
    private ItemVendaPostDto itemVendaPostDto;
    private List<ItemVendaPostDto> itemVendaPostLista;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        startItemVenda();
    }

    private void startItemVenda() {
        DateTimeFormatter dataFormatada = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        usuario = new Usuario();
        usuario.setId(ID_VENDEDOR);
        usuario.setNome(NOME_VENDEDOR);
        usuario.setLogin(LOGIN_VENDEDOR);
        usuario.setEmail(EMAIL_VENDEDOR);
        usuario.setDtNascimento(LocalDate.parse(NASCIMENTO, dataFormatada));
        usuario.setAdmin(true);

        comprador = new Comprador();
        comprador.setId(ID_COMPRADOR);
        comprador.setCpf(CPF);
        comprador.setEmail(EMAIL_COMPRADOR);
        comprador.setNome(NOME_CLIENTE);
        comprador.setTelefone(TELEFONE);

        itemVendaGetLista = new ArrayList<>();
        vendaGet = new VendaGetDto();
        vendaGet.setNomeCliente(NOME_CLIENTE);
        vendaGet.setEmail(EMAIL_COMPRADOR);
        vendaGet.setTelefone(TELEFONE);
        vendaGet.setTotalVenda(TOTAL_VENDA);
        vendaGet.setEndereco(ENDERECO);
        itemVendaGetDto = new ItemVendaGetDto();
        itemVendaGetDto.setUrlFoto("url");
        itemVendaGetDto.setQuantidade(12);
        itemVendaGetDto.setNome("uva");
        itemVendaGetDto.setSubtotal(BigDecimal.valueOf(399.90));
        itemVendaGetLista.add(itemVendaGetDto);
        vendaGet.setItens(itemVendaGetLista);

        itemVenda = new ArrayList<>();
        novaVenda = new Venda();
        novaVenda.setId(ID_VENDA);
        novaVenda.setComprador(comprador);
        novaVenda.setVendedor(usuario);
        novaVenda.setDataVenda(LocalDateTime.now());
        novaVenda.setTotalVenda(TOTAL_VENDA);
        novaVenda.setCep(CEP);
        novaVenda.setSiglaEstado(SIGLA_ESTADO);
        novaVenda.setCidade(CIDADE);
        novaVenda.setLogradouro(LOGRADOURO);
        novaVenda.setBairro(BAIRRO);
        novaVenda.setComplemento(COMPLEMENTO);
        novaVenda.setDataEntrega(LocalDate.parse(DATA_ENTREGA,dataFormatada));
        novoItemVenda = new ItemVenda();
        novoItemVenda.setVenda(novaVenda);
        novoItemVenda.setId(1L);
        novoItemVenda.setProduto(new Produto(1L,"uva", "Uva grada", "url", new BigDecimal(33.325) , true  ));
        novoItemVenda.setPrecoUnitario(BigDecimal.valueOf(33.325));
        novoItemVenda.setQuantidade(12);
        itemVenda.add(novoItemVenda);
        novaVenda.setItens(itemVenda);
        novaVenda.setVendaCancelada(false);

        itemVendaPostLista = new ArrayList<>();
        vendaPost = new VendaPostDto();
        vendaPost.setIdComprador(ID_COMPRADOR);
        vendaPost.setIdVendedor(ID_VENDEDOR);
        vendaPost.setSiglaEstado(SIGLA_ESTADO);
        vendaPost.setCep(CEP);
        vendaPost.setCidade(CIDADE);
        vendaPost.setLogradouro(LOGRADOURO);
        vendaPost.setBairro(BAIRRO);
        vendaPost.setComplemento(COMPLEMENTO);
        vendaPost.setDataEntrega(DATA_ENTREGA);
        itemVendaPostDto = new ItemVendaPostDto();
        itemVendaPostDto.setIdProduto(1L);
        itemVendaPostDto.setPrecoUnitario(BigDecimal.valueOf(33.325));
        itemVendaPostDto.setQuantidade(12);
        itemVendaPostLista.add(itemVendaPostDto);
        vendaPost.setItens(itemVendaPostLista);


    }
}
