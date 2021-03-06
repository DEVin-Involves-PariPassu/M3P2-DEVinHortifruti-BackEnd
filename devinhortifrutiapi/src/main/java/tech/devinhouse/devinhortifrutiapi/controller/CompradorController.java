package tech.devinhouse.devinhortifrutiapi.controller;

import com.sun.istack.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.devinhouse.devinhortifrutiapi.dto.CompradorDTO;
import tech.devinhouse.devinhortifrutiapi.model.Comprador;
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
            @RequestHeader("Authorization") String auth,
            @Valid @RequestBody CompradorDTO compradorDTO
    ) {
        compradorService.verificaAdmin(auth);
        Long idComprador = compradorService.salvar(compradorDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(idComprador).toUri();

        return ResponseEntity.created(location).body(idComprador);
    }

    @PutMapping(value = "/{id_comprador}")
    public ResponseEntity<Long> put(
            @NotNull @PathVariable Long id_comprador,
            @RequestHeader("Authorization") String auth,
            @Valid @RequestBody CompradorDTO compradorDTO) {

        if (id_comprador == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        compradorService.verificaAdmin(auth);
        compradorService.updateDoPut(id_comprador, compradorDTO);
        return ResponseEntity.ok(id_comprador);
    }

    @GetMapping
    public Comprador get(
            @RequestHeader("Authorization") String auth,
            @RequestParam String cpf
            ) {
                compradorService.verificaAdmin(auth);
                return this.compradorService.getComprador(cpf);
    }

}
