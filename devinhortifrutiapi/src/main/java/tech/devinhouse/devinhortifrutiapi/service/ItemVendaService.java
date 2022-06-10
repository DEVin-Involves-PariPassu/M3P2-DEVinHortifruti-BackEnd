package tech.devinhouse.devinhortifrutiapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.devinhouse.devinhortifrutiapi.dto.ItemVendaGetDto;
import tech.devinhouse.devinhortifrutiapi.dto.ItemVendaPostDto;
import tech.devinhouse.devinhortifrutiapi.model.ItemVenda;
import tech.devinhouse.devinhortifrutiapi.model.Produto;
import tech.devinhouse.devinhortifrutiapi.model.Venda;
import tech.devinhouse.devinhortifrutiapi.repository.ItemVendaRepository;
import tech.devinhouse.devinhortifrutiapi.repository.ProdutoRepository;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemVendaService {

    @Autowired
    ItemVendaRepository itemVendaRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Transactional
    public List<ItemVendaGetDto> listarItens(Venda venda) {
        List<ItemVenda> listItens = itemVendaRepository.findByVenda(venda);
        List<ItemVendaGetDto> listItensDto = converterItemVendaParaItemVendaDto(listItens);
        return listItensDto;
    }

    private List<ItemVendaGetDto> converterItemVendaParaItemVendaDto(List<ItemVenda> itens) {
        List<ItemVendaGetDto> listItensDto = new ArrayList<>();
        for (ItemVenda item : itens
        ) {
            ItemVendaGetDto itemDto = new ItemVendaGetDto();
            itemDto.setUrlFoto(item.getProduto().getUrlFoto());
            itemDto.setNome(item.getProduto().getNome());
            BigDecimal precoUnitario = item.getPrecoUnitario();
            itemDto.setQuantidade(item.getQuantidade());

            BigDecimal subTotal = precoUnitario.multiply(BigDecimal.valueOf(itemDto.getQuantidade()));
            itemDto.setSubtotal(subTotal);
            listItensDto.add(itemDto);
        }
        return listItensDto;
    }

    public void verificarSeOProdutoExiste(List<ItemVendaPostDto> listaItensDto) {
        for (ItemVendaPostDto item : listaItensDto
        ) {
            Long idProduto = item.getIdProduto();
            produtoRepository.findById(idProduto).orElseThrow(() -> new IllegalArgumentException("Produto inválido"));
        }
    }

    public BigDecimal calcularTotalDaVenda(List<ItemVendaPostDto> listaItensDto) {
        BigDecimal totalVenda = BigDecimal.ZERO;
        for (ItemVendaPostDto item : listaItensDto) {
            BigDecimal totalItem = item.getPrecoUnitario().multiply(new BigDecimal(item.getQuantidade()));
            totalVenda = totalVenda.add(totalItem);
        }
        return totalVenda;
    }

    public List<ItemVenda> converterItemVendaDtoEmItemVenda(List<ItemVendaPostDto> listaItensDto, Venda venda) {
        List<ItemVenda> listaItens = new ArrayList<>();
        for (ItemVendaPostDto item : listaItensDto
        ) {
            ItemVenda itemVenda = new ItemVenda();
            itemVenda.setVenda(venda);
            Produto produto = produtoRepository.findById(item.getIdProduto()).orElseThrow(()-> new IllegalArgumentException("Produto inválido"));
            itemVenda.setProduto(produto);
            itemVenda.setPrecoUnitario(item.getPrecoUnitario());
            itemVenda.setQuantidade(item.getQuantidade());
            listaItens.add(itemVenda);
        }
        return listaItens;

    }
}


