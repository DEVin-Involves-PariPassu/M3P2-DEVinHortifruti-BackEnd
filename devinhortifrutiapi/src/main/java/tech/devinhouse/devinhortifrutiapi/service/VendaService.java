package tech.devinhouse.devinhortifrutiapi.service;

import tech.devinhouse.devinhortifrutiapi.dto.ItemVendaGetDto;
import tech.devinhouse.devinhortifrutiapi.dto.VendaGetDto;
import tech.devinhouse.devinhortifrutiapi.model.Venda;
import tech.devinhouse.devinhortifrutiapi.repository.VendaRepository;
import tech.devinhouse.devinhortifrutiapi.repository.ItemVendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class VendaService {
    @Autowired
    VendaRepository vendaRepository;

    @Autowired
    ItemVendaService itemVendaService;

    @Transactional
    public VendaGetDto listarPorId (Long id){
        Venda venda = vendaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Venda não encontrada"));
        VendaGetDto vendaDto = converterVendaParaVendaDto(venda);
        return vendaDto;
    }

    public VendaGetDto converterVendaParaVendaDto(Venda venda){
        VendaGetDto vendaDto = new VendaGetDto();
        vendaDto.setId(venda.getId());
        vendaDto.setNomeCliente(venda.getComprador().getNome());
        vendaDto.setCpf(venda.getComprador().getCpf());
        vendaDto.setEmail(venda.getComprador().getEmail());
        vendaDto.setTelefone(venda.getComprador().getTelefone());
        vendaDto.setTotalVenda(venda.getTotalVenda());
        String complemento = venda.getComplemento() == null ? "" : String.format(" - %s", venda.getComplemento());
        String endereco = String.format("%s%s%n%s %s%n%s/%s",
                venda.getLogradouro(),
                complemento,
                venda.getCep(),
                venda.getBairro(),
                venda.getCidade(),
                venda.getSiglaEstado()
        );
        vendaDto.setEndereco(endereco);
        List<ItemVendaGetDto> itens = this.itemVendaService.listarItens(venda);
        vendaDto.setItens(itens);
        return vendaDto;
    }
}
