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
    public ResponseEntity<?> getProduto(@RequestParam Long id) {
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
           @Valid @RequestBody ProdutoDTO produtoDTO
    ){
        Long produtoId = produtoService.adicionaProduto(produtoDTO);
        return new ResponseEntity<>(produtoId, HttpStatus.CREATED);
    }


    @PutMapping(value = "/{id_produto}")
    public ResponseEntity<Long> putProduto(@NotNull @PathVariable Long id_produto,
                                    @RequestBody ProdutoDTO produtoDTO) {

        produtoService.atualizar(id_produto, produtoDTO);
        return ResponseEntity.ok(id_produto);
    }

}
