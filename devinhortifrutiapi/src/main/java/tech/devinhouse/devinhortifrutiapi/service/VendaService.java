package tech.devinhouse.devinhortifrutiapi.service;


import tech.devinhouse.devinhortifrutiapi.dto.VendaPostDto;
import tech.devinhouse.devinhortifrutiapi.model.Venda;
import tech.devinhouse.devinhortifrutiapi.repository.VendaRepository;
import tech.devinhouse.devinhortifrutiapi.repository.UsuarioRepository;
import tech.devinhouse.devinhortifrutiapi.repository.ItemVendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class VendaService {
    @Autowired
    VendaRepository vendaRepository;

    @Autowired
    ItemVendaRepository itemVendaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ItemVendaService itemVendaService;

    public Long salvarVenda(VendaPostDto vendaPostDto){

    }

    public void validarSeVendedorExiste(Long idVendedor){
        usuarioRepository.findById(idVendedor).orElseThrow(() -> new IllegalArgumentException("Id do vendedor é inválido "));
    }

    public void validarSeCompradorExiste(Long idComprador){
        usuarioRepository.findById(idComprador).orElseThrow(() -> new IllegalArgumentException("Id do comprador é inválido "));
    }

    public void validarSeProdutoExiste(VendaPostDto vendaPostDto){

    }

    public BigDecimal calcularTotalDaCompra(VendaPostDto vendaPostDto) {
        BigDecimal totalCompra = itemVendaService.calcularTotalDaVenda(vendaPostDto.getItens());
        return totalCompra;
    }


    private Venda converterVendaDtoEmVenda(VendaPostDto vendaPostDto) {

        var venda = new Venda();
        venda.setVendedor(vendaPostDto.getIdVendedor());
        venda.setComprador(vendaPostDto.getIdComprador());
        venda.setCep(vendaPostDto.getCep());
        venda.setSiglaEstado(vendaPostDto.getSiglaEstado());
        venda.setCidade(vendaPostDto.getCidade());
        venda.setLogradouro(vendaPostDto.getLogradouro());
        venda.setBairro(vendaPostDto.getBairro());
        venda.setComplemento(vendaPostDto.getComplemento());
        venda.setDataEntrega(LocalDate.parse(vendaPostDto.getDataEntrega()));
        venda.setItens(itemVendaService.converterItemVendaDtoEmItemVenda(vendaPostDto.getItens()));
        venda.setDataVenda(LocalDateTime.now());
        venda.setTotalVenda(calcularTotalDaCompra(vendaPostDto));

        return venda;
    }

    public void enviarEmail(){

    }

    public void enviarSMS(VendaPostDto vendaPostDto){

    }



}
