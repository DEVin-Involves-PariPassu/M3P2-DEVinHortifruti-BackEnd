package tech.devinhouse.devinhortifrutiapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.devinhouse.devinhortifrutiapi.dto.CompradorDTO;
import tech.devinhouse.devinhortifrutiapi.service.CompradorService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/comprador")
public class CompradorController {

    @Autowired
    CompradorService compradorService;

    @PostMapping
    public ResponseEntity<Long> post(
//            @RequestHeader("Authorization") String auth,
            @Valid @RequestBody CompradorDTO comprador
    ) {
        Long id = compradorService.salvar(comprador);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id).toUri();

        return ResponseEntity.created(location).body(id);
    }

}
