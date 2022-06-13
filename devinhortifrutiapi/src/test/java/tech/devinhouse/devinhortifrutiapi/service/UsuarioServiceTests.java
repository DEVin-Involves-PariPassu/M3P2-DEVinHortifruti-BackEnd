package tech.devinhouse.devinhortifrutiapi.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import tech.devinhouse.devinhortifrutiapi.model.Usuario;
import tech.devinhouse.devinhortifrutiapi.repository.UsuarioRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class UsuarioServiceTests {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService service;

    @Test
    @DisplayName("Listar Usuario Por Id")
    public void deveListarUsuarioPorId(){
        //Cenário
        Usuario usuario = new Usuario();
        usuario.setLogin("maria");
        usuario.setEmail("maria@email.com.br");
        usuario.setId(1L);

        when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));
        // Execução
        Usuario usuarioRetornado = service.listarPorId(usuario.getId());
        // Validação
        Assertions.assertEquals(usuario, usuarioRetornado);
        verify(this.usuarioRepository, times(1)).findById(usuario.getId());
    }

}
