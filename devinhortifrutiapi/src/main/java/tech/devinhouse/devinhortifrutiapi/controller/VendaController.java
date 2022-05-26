package tech.devinhouse.devinhortifrutiapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.devinhouse.devinhortifrutiapi.dto.VendaPostDto;
import tech.devinhouse.devinhortifrutiapi.service.VendaService;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    @Autowired
    VendaService vendaService;


    @PostMapping
    public ResponseEntity<Long> post(
            @RequestHeader("Authorization") String autorizacaoUsuario,
            @RequestBody VendaPostDto vendaPostDto
    ) {
        Long novaVendaId = vendaService.salvarVenda(autorizacaoUsuario, vendaPostDto);
        return new ResponseEntity<>(novaVendaId, HttpStatus.CREATED);
    }


}
