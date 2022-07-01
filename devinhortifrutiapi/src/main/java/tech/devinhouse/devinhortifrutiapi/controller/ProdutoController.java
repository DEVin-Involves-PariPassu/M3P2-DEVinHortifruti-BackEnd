package tech.devinhouse.devinhortifrutiapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.devinhouse.devinhortifrutiapi.security.TokenService;
import tech.devinhouse.devinhortifrutiapi.dto.ProdutoDTO;
import tech.devinhouse.devinhortifrutiapi.dto.UsuarioDTO;
import tech.devinhouse.devinhortifrutiapi.model.Produto;
import tech.devinhouse.devinhortifrutiapi.service.ProdutoService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.net.URI;


@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;

    @Autowired
    private TokenService tokenService;

    @GetMapping
    public ResponseEntity<Page<Produto>> getListaProdutos(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false, defaultValue = "1") Integer pagina,
            @RequestParam(required = false, defaultValue = "10") Integer tamanhoPagina,
            @RequestHeader("Authorization") String auth
    ) throws JsonProcessingException {
        String token = auth.substring(7);
        UsuarioDTO usuario = tokenService.getUsuario(token);

        if (!usuario.getIsAdmin()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        Page<Produto> produtos = produtoService.listarProdutos(nome, pagina, tamanhoPagina);
        if (produtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(produtos);
    }

    @GetMapping(value = "/{id_produto}")
    public ResponseEntity<Produto> get(
            @NotBlank @PathVariable(name = "id_produto") Long id,
            @RequestHeader("Authorization") String auth
    ) {
        produtoService.verificaAdmin(auth);
        Produto produto = produtoService.getProduto(id);
        return ResponseEntity.ok(produto);
    }

    @PostMapping
    public ResponseEntity<Long> postProduto(
            @RequestHeader("Authorization") String auth,
            @Valid @RequestBody ProdutoDTO produtoDTO
    ){
        produtoService.verificaAdmin(auth);
        Long produtoId = produtoService.adicionaProduto(produtoDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(produtoId).toUri();
        return ResponseEntity.created(location).body(produtoId);
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
