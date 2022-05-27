package tech.devinhouse.devinhortifrutiapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.devinhouse.devinhortifrutiapi.dto.VendaGetDto;
import tech.devinhouse.devinhortifrutiapi.service.VendaService;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    @Autowired
    VendaService vendaService;

    @GetMapping("/{id_venda}")
    public ResponseEntity<VendaGetDto> getPorId(
            @PathVariable(name = "id_venda") Long idVenda,
            @RequestHeader("Authorization") String auth
    ){
        VendaGetDto venda = vendaService.listarPorId(idVenda);
        return ResponseEntity.ok(venda);
    }
}
