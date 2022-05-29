package tech.devinhouse.devinhortifrutiapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.devinhouse.devinhortifrutiapi.dto.EmailDto;
import tech.devinhouse.devinhortifrutiapi.dto.SmsDto;
import tech.devinhouse.devinhortifrutiapi.dto.VendaPostDto;
import tech.devinhouse.devinhortifrutiapi.dto.VendaGetDto;
import tech.devinhouse.devinhortifrutiapi.service.RabbitMQService;
import tech.devinhouse.devinhortifrutiapi.service.VendaService;

import javax.validation.Valid;
import java.text.NumberFormat;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    @Autowired
    VendaService vendaService;

    @Autowired
    RabbitMQService rabbitMQService;

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
                        "O vendador está preparando seu pedido e em breve estará a caminho!",
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
            @PathVariable(name = "id_venda") Long idVenda
            //@RequestHeader("Authorization") String auth
    ){
        VendaGetDto venda = vendaService.listarPorId(idVenda);
        return ResponseEntity.ok(venda);
    }

}
