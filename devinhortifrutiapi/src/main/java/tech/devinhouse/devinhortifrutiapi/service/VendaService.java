package tech.devinhouse.devinhortifrutiapi.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tech.devinhouse.devinhortifrutiapi.dto.ItemVendaPostDto;
import tech.devinhouse.devinhortifrutiapi.dto.VendaPostDto;
import tech.devinhouse.devinhortifrutiapi.model.ItemVenda;
import tech.devinhouse.devinhortifrutiapi.model.Venda;
import tech.devinhouse.devinhortifrutiapi.repository.VendaRepository;
import tech.devinhouse.devinhortifrutiapi.repository.UsuarioRepository;
import tech.devinhouse.devinhortifrutiapi.repository.ItemVendaRepository;
import tech.devinhouse.devinhortifrutiapi.service.ItemVendaService;
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

    }


    private Venda converterVendaDtoEmVenda(VendaPostDto vendaPostDto) {

    }

    public void enviarEmail(){

    }

    public void enviarSMS(VendaPostDto vendaPostDto){

    }



}
