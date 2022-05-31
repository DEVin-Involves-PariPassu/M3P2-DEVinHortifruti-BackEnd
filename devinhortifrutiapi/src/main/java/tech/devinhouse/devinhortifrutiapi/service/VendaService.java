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
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    @Transactional
    List<VendaGetDto> listarVendas(Long idUsuario, String cpf, String nome, BigDecimal valorTotal) {

        if (idUsuario != null && cpf == null && nome == null && valorTotal == null) {
            Optional<Venda> listaUsuario = Optional.ofNullable(vendaRepository.findById(idUsuario).orElseThrow(
                    () -> new EntityNotFoundException("Id não localizado")
            ));
            Venda vendaEntity = listaUsuario.get();
            List<VendaGetDto> vendaGetDto  = converteVendaDTO(vendaRepository.findUsuarioByIdUsuario(vendaEntity));
            return vendaGetDto;

        } else if (idUsuario == null && cpf != null && nome == null && valorTotal == null) {
            Optional<Venda> listaCpf = Optional.ofNullable(vendaRepository.findById(Long.valueOf(cpf)).orElseThrow(
                    () -> new EntityNotFoundException("Cpf não localizado")));
            Venda vendaEntity = listaCpf.get();
            List<VendaGetDto> vendaDTO = converteVendaDTO(vendaRepository.findVendaByCpf(vendaEntity));
            return vendaDTO;

        }
        Optional<Venda> listaUsuario = Optional.ofNullable(vendaRepository.findById(idUsuario).orElseThrow(
                () -> new EntityNotFoundException("Id não encontrada")));
        Venda vendaEntity = listaUsuario.get();

        Optional<Venda> listaVenda = Optional.ofNullable(vendaRepository.findById(Long.valueOf(cpf)).orElseThrow(
                () -> new EntityNotFoundException("CPF não encontrado")));
        Venda usuarioEntity = listaVenda.get();
        List<VendaGetDto> vendaDTO = converteVendaDTO(vendaRepository.findVendaByIdAndCpf(usuarioEntity,vendaEntity));

        return vendaDTO;
    }

    @Transactional
    public List<VendaGetDto> listar(Long idUsuario, String cpf, String nome, BigDecimal valorTotal ) {
        if (idUsuario == null && cpf == null && nome == null && valorTotal == null) {
            List<Venda> lista = vendaRepository.findAll();
            List<VendaGetDto> listaDTO = converteVendaDTO(lista);
            if (lista.isEmpty()) {
                throw new NoSuchElementException("Não existem vendas");
            }
            return listaDTO;
        }
        List<VendaGetDto> listaDTO = listarVendas(idUsuario, cpf, nome, valorTotal);
        return listaDTO;
    }

    private List<VendaGetDto> converteVendaDTO(List<Venda> list){
        List<VendaGetDto> listaDTO = new ArrayList<>();
        for (Venda venda : list
        ) {
            VendaGetDto vendaDTO = new VendaGetDto();
            vendaDTO.setId(venda.getId());
            vendaDTO.setCpf(venda.getCpf());
            vendaDTO.setNomeCliente(venda.getNome());
            vendaDTO.setTotalVenda(venda.getTotalVenda());
            listaDTO.add(vendaDTO);
        }
        return listaDTO;
    }
}
