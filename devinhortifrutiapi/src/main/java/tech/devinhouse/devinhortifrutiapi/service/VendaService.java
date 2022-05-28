package tech.devinhouse.devinhortifrutiapi.service;


import tech.devinhouse.devinhortifrutiapi.dto.VendaPostDto;

import tech.devinhouse.devinhortifrutiapi.dto.ItemVendaGetDto;
import tech.devinhouse.devinhortifrutiapi.dto.VendaGetDto;
import tech.devinhouse.devinhortifrutiapi.model.ItemVenda;
import tech.devinhouse.devinhortifrutiapi.model.Venda;
import tech.devinhouse.devinhortifrutiapi.repository.CompradorRepository;
import tech.devinhouse.devinhortifrutiapi.repository.VendaRepository;
import tech.devinhouse.devinhortifrutiapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class VendaService {
    @Autowired
    VendaRepository vendaRepository;

    @Autowired
    ItemVendaService itemVendaService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    CompradorRepository compradorRepository;


    public Venda salvarVenda(VendaPostDto vendaPostDto){
        validarSeVendedorExiste(vendaPostDto.getIdVendedor());
        validarSeCompradorExiste(vendaPostDto.getIdComprador());
        validarSeProdutoExiste(vendaPostDto);
        var novaVenda = converterVendaDtoEmVenda(vendaPostDto);
        return vendaRepository.save(novaVenda);
    }

    public void validarSeVendedorExiste(Long idVendedor){
        usuarioRepository.findById(idVendedor).orElseThrow(() -> new IllegalArgumentException("Id do vendedor é inválido "));
    }

    public void validarSeCompradorExiste(Long idComprador){
        compradorRepository.findById(idComprador).orElseThrow(() -> new IllegalArgumentException("Id do comprador é inválido "));
    }

    public void validarSeProdutoExiste(VendaPostDto vendaPostDto){
        itemVendaService.verificarSeOProdutoExiste(vendaPostDto.getItens());
    }

    public BigDecimal calcularTotalDaCompra(VendaPostDto vendaPostDto) {
        BigDecimal totalCompra = itemVendaService.calcularTotalDaVenda(vendaPostDto.getItens());
        return totalCompra;
    }

    private LocalDate formatarDataDeEntrega(VendaPostDto vendaPostDto) throws DateTimeParseException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dtEntrega = LocalDate.parse(vendaPostDto.getDataEntrega(), dateTimeFormatter);
        return dtEntrega;
    }

    private Venda converterVendaDtoEmVenda(VendaPostDto vendaPostDto) {

        var venda = new Venda();
        var vendedor = usuarioRepository.findById(vendaPostDto.getIdVendedor()).orElseThrow(() -> new IllegalArgumentException("Id do vendedor é inválido "));
        var comprador = compradorRepository.findById(vendaPostDto.getIdComprador()).orElseThrow(() -> new IllegalArgumentException("Id do comprador é inválido "));
        venda.setVendedor(vendedor);
        venda.setComprador(comprador);
        venda.setCep(vendaPostDto.getCep());
        venda.setSiglaEstado(vendaPostDto.getSiglaEstado());
        venda.setCidade(vendaPostDto.getCidade());
        venda.setLogradouro(vendaPostDto.getLogradouro());
        venda.setBairro(vendaPostDto.getBairro());
        venda.setComplemento(vendaPostDto.getComplemento());
        venda.setDataEntrega(formatarDataDeEntrega(vendaPostDto));
        venda.setDataVenda(LocalDateTime.now());
        venda.setTotalVenda(calcularTotalDaCompra(vendaPostDto));
        List<ItemVenda> itens = itemVendaService.converterItemVendaDtoEmItemVenda(vendaPostDto.getItens(), venda);
        venda.setItens(itens);
        return venda;
    }

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
