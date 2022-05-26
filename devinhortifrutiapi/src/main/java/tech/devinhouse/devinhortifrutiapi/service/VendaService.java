package tech.devinhouse.devinhortifrutiapi.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tech.devinhouse.devinhortifrutiapi.dto.VendaPostDto;
import tech.devinhouse.devinhortifrutiapi.model.ItemVenda;
import tech.devinhouse.devinhortifrutiapi.model.Venda;
import tech.devinhouse.devinhortifrutiapi.repository.VendaRepository;
import tech.devinhouse.devinhortifrutiapi.repository.UsuarioRepository;
import tech.devinhouse.devinhortifrutiapi.repository.ItemVendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class VendaService {
    @Autowired
    VendaRepository vendaRepository;

    @Autowired
    ItemVendaRepository itemVendaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TokenService tokenService;

    public Long salvarVenda(String autorizacaoUsuario, VendaPostDto vendaPostDto){
        if (verificarPrivacidadeDaRota(autorizacaoUsuario)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        validarSeVendedorExiste(vendaPostDto.getIdVendedor());
        validarSeCompradorExiste(vendaPostDto.getIdComprador());
        validarSeProdutoExiste(vendaPostDto);
        calcularTotalDaCompra();
        //Se não houver nenhum erro, deve-se criar uma venda na tabela de venda junto com os
        //itens na tabela de item_venda em cascata.
        Venda novaVenda = converterVendaDtoEmVenda(vendaPostDto);
        vendaRepository.save(novaVenda);
        enviarEmail();
        enviarSMS();
        return novaVenda.getId();

    }



    public boolean verificarPrivacidadeDaRota(String autorizacaoUsuario) {
        //A rota tem que ser privada
        String token = autorizacaoUsuario.substring(7);
        Long idUsuario = tokenService.getIdUsuario(token);
        Usuario usuarioLogado = usuarioRepository.findById(idUsuario)
                .orElseThrow(
                        ()-> new IllegalArgumentException()
                );
        if (usuarioLogado !== usuarioAdministrador) {
            return true;
        }
        return false;
    }

    public void validarSeVendedorExiste(Long idVendedor){
        //Validar se vendedor existe
        usuarioRepository.findById(idVendedor).orElseThrow(() -> new IllegalArgumentException("Id do vendedor é inválido "));
        //Deve-se retornar o Status de Erro 400 (Bad Request) no caso de erro com as validações
    }

    public void validarSeCompradorExiste(Long idComprador){
        //Validar se o comprador existe
        usuarioRepository.findById(idComprador).orElseThrow(() -> new IllegalArgumentException("Id do comprador é inválido "));
        //Deve-se retornar o Status de Erro 400 (Bad Request) no caso de erro com as validações
    }

    public void validarSeProdutoExiste(VendaPostDto vendaPostDto){
        //Validar se o produto existe
        Long idProduto = vendaPostDto.getItens().get().getIdProduto();
        itemVendaRepository.findById(idProduto).orElseThrow(() -> new IllegalArgumentException("Produto inválido"));
        //Deve-se retornar o Status de Erro 400 (Bad Request) no caso de erro com as validações
    }

    public void calcularTotalDaCompra(){
        //Realizar o cálculo do total e armazenar na tabela de venda, junto com a data da venda.
    }

    private Venda converterVendaDtoEmVenda(VendaPostDto vendaPostDto) {
        //converter dados do body para os dados da entidade
    }

    public void enviarEmail(){
        //Deve-se enviar um e-mail de confirmação para o comprador da venda realizada
    }

    public void enviarSMS(){
        //Caso o
        //telefone tenha sido informado na mensagem de notificação, deve-se printar no log a
        //mensagem.
    }



}
