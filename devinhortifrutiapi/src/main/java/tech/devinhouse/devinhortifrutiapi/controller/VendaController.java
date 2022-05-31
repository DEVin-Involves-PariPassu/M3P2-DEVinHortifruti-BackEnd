package tech.devinhouse.devinhortifrutiapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.devinhouse.devinhortifrutiapi.configuration.TokenService;
import tech.devinhouse.devinhortifrutiapi.dto.EmailDto;
import tech.devinhouse.devinhortifrutiapi.dto.SmsDto;
import tech.devinhouse.devinhortifrutiapi.dto.VendaPostDto;
import tech.devinhouse.devinhortifrutiapi.dto.VendaGetDto;
import tech.devinhouse.devinhortifrutiapi.model.Venda;
import tech.devinhouse.devinhortifrutiapi.repository.VendaRepository;
import tech.devinhouse.devinhortifrutiapi.service.RabbitMQService;
import tech.devinhouse.devinhortifrutiapi.service.VendaService;

import javax.validation.Valid;
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
            @Valid @RequestBody VendaPostDto vendaPostDto
    ) {
        var novaVenda = vendaService.salvarVenda( vendaPostDto);
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
        return new ResponseEntity<>(novaVenda.getId(), HttpStatus.CREATED);
    }

    @GetMapping("/{id_venda}")
    public ResponseEntity<VendaGetDto> getPorId(
            @PathVariable(name = "id_venda") Long idVenda,
            @RequestHeader("Authorization") String auth
    ){
        VendaGetDto venda = vendaService.listarPorId(idVenda);
        return ResponseEntity.ok(venda);
    }

    @GetMapping("/vendas")
    public ResponseEntity<List<Venda>> get(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestHeader("Authorization") String auth
    ) {
        String token = auth.substring(7);
        Long idUsuario = tokenService.getUsuarioPorId(token);
        Venda loggedUser = vendaRepository.findById(idUsuario)
                .orElseThrow(
                        ()-> new IllegalArgumentException()
                );

        if (!loggedUser.canRead("vendas")) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        List<VendaGetDto> listVenda = vendaService.listar(nome, cpf, page, size);
        if (listVenda.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listVenda);
    }

}
