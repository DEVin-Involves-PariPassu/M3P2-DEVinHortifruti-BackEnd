package tech.devinhouse.devinhortifrutiapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tech.devinhouse.devinhortifrutiapi.dto.EmailDto;
import tech.devinhouse.devinhortifrutiapi.dto.UsuarioDTO;
import tech.devinhouse.devinhortifrutiapi.model.Usuario;
import tech.devinhouse.devinhortifrutiapi.service.RabbitMQService;
import tech.devinhouse.devinhortifrutiapi.service.UsuarioService;
import tech.devinhouse.devinhortifrutiapi.util.GeradorDeSenha;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @Autowired
    private RabbitMQService rabbitMQService;

    @GetMapping

    public ResponseEntity<Page<Usuario>> getListaUsuarios(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String login,
            @RequestParam(required = false, defaultValue = "0") Integer totalDePaginas,
            @RequestParam(required = false, defaultValue = "2") Integer totalPorPaginas,
            @RequestHeader("Authorization") String auth
    ) throws JsonProcessingException {

        if(!service.usuarioEhAdmin(auth)){

            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Page<Usuario> usuarios = service.listarTodosOsUsuarios(id, nome, login, totalPorPaginas);

        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id_usuario}")
    public ResponseEntity<Usuario> getPorId (
            @RequestHeader("Authorization") String auth,
            @PathVariable(name = "id_usuario") Long idUsuario) throws JsonProcessingException {

        if(!service.usuarioEhAdmin(auth)){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Usuario usuario = service.listarPorId(idUsuario);

        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    public ResponseEntity<Long> post(
            @RequestHeader("Authorization") String auth,
            @Valid
            @RequestBody UsuarioDTO usuarioDTO) {

        Usuario usuario = service.salvar(usuarioDTO);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String senhaTextoPlano = GeradorDeSenha.generatePassayPassword();
        // bcript senha antes de salvar no banco
        String senha = encoder.encode(senhaTextoPlano);
        usuario = service.salvarUsuarioComSenha(usuario, senha);
        EmailDto emailDto = new EmailDto();

        emailDto.setDestinatario(usuario.getEmail());
        emailDto.setTitulo("Dev in Hortifruti - Usuário criado com sucesso");

        String mensagem = String.format("Prezado %s, sua conta foi criada com sucesso! %n" +
                        "Sua senha é %s",
                usuario.getNome(), senhaTextoPlano
        );

        emailDto.setMensagem(mensagem);
        rabbitMQService.enviarEmail(emailDto);
        return new ResponseEntity<>(usuario.getId(), HttpStatus.CREATED);
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
            @RequestHeader("Authorization") String auth,
            @PathVariable(name = "id_usuario") Long idUsuario) throws JsonProcessingException {

        if(!service.usuarioEhAdmin(auth)){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        service.delete(idUsuario);

        return ResponseEntity.noContent().build();
    }
}
