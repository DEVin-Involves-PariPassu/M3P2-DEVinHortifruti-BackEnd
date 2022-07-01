package tech.devinhouse.devinhortifrutiapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.devinhouse.devinhortifrutiapi.security.TokenService;
import tech.devinhouse.devinhortifrutiapi.dto.*;
import tech.devinhouse.devinhortifrutiapi.model.Venda;
import tech.devinhouse.devinhortifrutiapi.repository.VendaRepository;
import tech.devinhouse.devinhortifrutiapi.service.RabbitMQService;
import tech.devinhouse.devinhortifrutiapi.service.VendaService;

import javax.validation.Valid;
import java.net.URI;
import java.text.NumberFormat;
import java.util.List;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    @Autowired
    VendaService vendaService;

    @Autowired
    RabbitMQService rabbitMQService;

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<Long> post(
            @RequestHeader("Authorization") String auth,
            @Valid @RequestBody VendaPostDto vendaPostDto
    ) {
        vendaService.verificaAdmin(auth);
        var novaVenda = vendaService.salvarVenda( vendaPostDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novaVenda.getId()).toUri();

        EmailDto emailDto = new EmailDto();
        emailDto.setDestinatario(novaVenda.getComprador().getEmail());
        emailDto.setTitulo("Dev in Hortifruti - Detalhes da sua compra");

        String valorTotalDaCompra = NumberFormat.getCurrencyInstance().format(novaVenda.getTotalVenda());

        String mensagem = String.format("Prezado(a) %s, sua compra foi realizada com sucesso! %n" +
                        "Valor total da compra: %s %n" +
                        "O vendedor está preparando seu pedido e em breve estará a caminho!",
                novaVenda.getComprador().getNome(), valorTotalDaCompra
        );

        emailDto.setMensagem(mensagem);
        rabbitMQService.enviarEmail(emailDto);

        if(!novaVenda.getComprador().getTelefone().isEmpty()){
            SmsDto smsDto = new SmsDto(novaVenda.getComprador().getTelefone(), mensagem);
            rabbitMQService.enviarSms(smsDto);
        }

        return ResponseEntity.created(location).body(novaVenda.getId());
    }

    @GetMapping("/{id_venda}")
    public ResponseEntity<VendaGetDto> getPorId(
            @RequestHeader("Authorization") String auth,
            @PathVariable(name = "id_venda") Long idVenda

    ){
        vendaService.verificaAdmin(auth);
        VendaGetDto venda = vendaService.listarPorId(idVenda);
        return ResponseEntity.ok(venda);
    }

    @GetMapping
    public ResponseEntity<VendaListaGetDto> get(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false, defaultValue = "1") Integer numeroPagina,
            @RequestParam(required = false, defaultValue = "5") Integer tamanho,
            @RequestHeader("Authorization") String auth
    ) throws JsonProcessingException {
        String token = auth.substring(7);
        UsuarioDTO usuario = tokenService.getUsuario(token);

        VendaListaGetDto vendaListaGetDto = vendaService.listarVendas(nome, cpf, numeroPagina, tamanho, usuario);
        if (vendaListaGetDto.getVendas().isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(vendaListaGetDto);
    }

    @PutMapping("/{id_venda}")
    public ResponseEntity<Void> deletaVenda(
            @PathVariable(name = "id_venda") Long idVenda,
            @RequestHeader("Authorization") String auth)
        {

        Venda venda = vendaService.cancelarVenda(idVenda);
        EmailDto emailDto = new EmailDto();
        emailDto.setDestinatario(venda.getComprador().getEmail());
        emailDto.setTitulo("Dev in Hortifruti - Compra Cancelada - pedido " + idVenda);

        String mensagem = String.format("Prezado(a) %s, sua compra pedido %s foi cancelada com sucesso!",
                venda.getComprador().getNome(), venda.getId());

        emailDto.setMensagem(mensagem);
        rabbitMQService.enviarEmail(emailDto);

        if(!venda.getComprador().getTelefone().isEmpty()){
            SmsDto smsDto = new SmsDto(venda.getComprador().getTelefone(), mensagem);
            rabbitMQService.enviarSms(smsDto);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
