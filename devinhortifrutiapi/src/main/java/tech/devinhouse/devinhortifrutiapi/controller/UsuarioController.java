package tech.devinhouse.devinhortifrutiapi.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.core.token.TokenService;
import org.springframework.web.bind.annotation.*;
import tech.devinhouse.devinhortifrutiapi.dto.UsuarioDTO;
import tech.devinhouse.devinhortifrutiapi.model.Usuario;
import tech.devinhouse.devinhortifrutiapi.repository.UsuarioRepository;
import tech.devinhouse.devinhortifrutiapi.service.UsuarioService;

import javax.validation.Valid;
import java.util.List;

public class UsuarioController {

    @Autowired
    private UsuarioService service;

   // @Autowired
    //private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<List<Usuario>> get(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String dtNascimentoMin,
            @RequestParam(required = false) String dtNascimentoMax
            // @RequestHeader("Authorization") String auth
    ) {

        List<Usuario> usuarios = service.listar(nome, dtNascimentoMin, dtNascimentoMax);
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<Long> post(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        Long idUsuario = service.salvar(usuarioDTO);

        return new ResponseEntity<>(idUsuario, HttpStatus.CREATED);
    }

    @PutMapping("/{id_usuario}")
    public ResponseEntity<Void> put(@RequestHeader("Authorization") String auth,
                                    @PathVariable(name = "id_usuario") Long idUsuario,
                                    @Valid @RequestBody UsuarioDTO usuarioDTO) {

        service.atualizar(idUsuario, usuarioDTO);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id_usuario}")
    public ResponseEntity<List<Usuario>> delete(
            //@RequestHeader("Authorization") String auth,
            @PathVariable(name = "id_usuario") Long idUsuario) {

        service.delete(idUsuario);

        return ResponseEntity.noContent().build();
    }

}