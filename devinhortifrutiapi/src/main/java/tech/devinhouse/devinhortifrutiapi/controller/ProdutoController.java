package tech.devinhouse.devinhortifrutiapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.devinhouse.devinhortifrutiapi.dto.ProdutoDTO;
import tech.devinhouse.devinhortifrutiapi.service.ProdutoService;
import javax.persistence.EntityNotFoundException;
import javax.validation.*;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;

    @GetMapping(value = "/{id_produto}")
    public ResponseEntity<?> getProduto(
            @RequestHeader("Authorization") String auth,
            @RequestParam Long id) {
        produtoService.verificaAdmin(auth);
        try{
            return ResponseEntity.ok(this.produtoService.getProduto(id));
        }catch(EntityNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping
    public ResponseEntity<Long> postProduto(
            @RequestHeader("Authorization") String auth,
            @Valid @RequestBody ProdutoDTO produtoDTO
    ){
        produtoService.verificaAdmin(auth);
        Long produtoId = produtoService.adicionaProduto(produtoDTO);
        return new ResponseEntity<>(produtoId, HttpStatus.CREATED);
    }


    @PutMapping(value = "/{id_produto}")
    public ResponseEntity<Long> putProduto(
            @RequestHeader("Authorization") String auth,
            @NotNull @PathVariable Long id_produto,
            @Valid @RequestBody ProdutoDTO produtoDTO) {

        produtoService.verificaAdmin(auth);
        produtoService.atualizar(id_produto, produtoDTO);
        return ResponseEntity.ok(id_produto);
    }

}
