package tech.devinhouse.devinhortifrutiapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import tech.devinhouse.devinhortifrutiapi.dto.ItemVendaGetDto;
import tech.devinhouse.devinhortifrutiapi.model.ItemVenda;
import tech.devinhouse.devinhortifrutiapi.model.Venda;
import tech.devinhouse.devinhortifrutiapi.repository.ItemVendaRepository;
import tech.devinhouse.devinhortifrutiapi.repository.VendaRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ItemVendaService {

    @Autowired
    VendaRepository vendaRepository;

    @Autowired
    ItemVendaRepository itemVendaRepository;

    @Transactional
    public List<ItemVendaGetDto> listarItens (Venda venda){
        List <ItemVenda> listItens = itemVendaRepository.findByVenda(venda);
        List<ItemVendaGetDto> listItensDto = converterItemVendaParaItemVendaDto(listItens);
        return listItensDto;
    }

    private List<ItemVendaGetDto> converterItemVendaParaItemVendaDto(List<ItemVenda> itens){
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
}
