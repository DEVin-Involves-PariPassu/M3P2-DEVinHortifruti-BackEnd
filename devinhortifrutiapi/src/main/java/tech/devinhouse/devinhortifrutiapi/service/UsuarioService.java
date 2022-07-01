package tech.devinhouse.devinhortifrutiapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.devinhouse.devinhortifrutiapi.security.TokenService;
import tech.devinhouse.devinhortifrutiapi.dto.UsuarioDTO;
import tech.devinhouse.devinhortifrutiapi.model.Usuario;
import tech.devinhouse.devinhortifrutiapi.repository.UsuarioRepository;
import tech.devinhouse.devinhortifrutiapi.service.exception.RequiredFieldMissingException;
import tech.devinhouse.devinhortifrutiapi.service.exception.UserIsUnderAgeException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    public List<Usuario> listar(String nome, String dtNascimentoMinStr, String dtNascimentoMaxStr) {
        LocalDate dtNascimentoMin = verificationDate(dtNascimentoMinStr);
        LocalDate dtNascimentoMax = verificationDate(dtNascimentoMaxStr);
        return usuarioRepository.findAll(
                Specification.where(
                        SpecificationsUsuario.nome(nome).and(
                                SpecificationsUsuario.dtNascimentoMin(dtNascimentoMin).and(
                                        SpecificationsUsuario.dtNascimentoMax(dtNascimentoMax)
                                )
                        )
                )
        );
    }

    public Usuario listarPorId(Long id){
        return usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }


    public Usuario salvar(UsuarioDTO usuarioDTO) {
        Usuario usuario = validateUser(usuarioDTO);
        return usuarioRepository.save(usuario);
    }

    public Usuario salvarUsuarioComSenha(Usuario usuario, String senha) {
        usuario.setSenha(senha);
        return usuarioRepository.save(usuario);
    }


    public void atualizar(Long idUser, UsuarioDTO usuarioDTO) {
        Usuario usuario = updateUser(idUser, usuarioDTO);

        usuarioRepository.save(usuario);
    }

    public Usuario updateUser(Long idUser, UsuarioDTO usuarioDTO) {
        isUniqueEmailUser(usuarioDTO);
        isUniqueLoginUser(usuarioDTO);
        LocalDate dtNascimento = verificationDate(String.valueOf(usuarioDTO));
        verificationAge(dtNascimento);

        Usuario usuario = usuarioRepository.findById(idUser).orElseThrow(
                () -> new EntityNotFoundException("Id de usuário inexistente.")
        );
        usuario.setLogin(usuarioDTO.getLogin());
        usuario.setNome(usuarioDTO.getNome());
        usuario.setDtNascimento(dtNascimento);

        return usuario;
    }

    private Usuario validateUser(UsuarioDTO usuarioDTO) {

        existsNome(usuarioDTO);
        existsLogin(usuarioDTO);
        existsDtNascimento(usuarioDTO);
        existsIsAdmin(usuarioDTO);
        existsEmail(usuarioDTO);
        isUniqueEmailUser(usuarioDTO);
        isUniqueLoginUser(usuarioDTO);
        LocalDate usuarioAge = verificationDate(usuarioDTO.getDtNascimento());
        verificationAge(usuarioAge);


        //BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        Usuario newUser = new Usuario();
        newUser.setLogin(usuarioDTO.getLogin());
        newUser.setNome(usuarioDTO.getNome());
        newUser.setDtNascimento(usuarioAge);
        newUser.setEmail(usuarioDTO.getEmail());
        newUser.setIsAdmin(usuarioDTO.getIsAdmin());
        newUser = usuarioRepository.save(newUser);

        return newUser;
    }

    private void existsNome(UsuarioDTO usuarioDTO) {
        if (usuarioDTO.getNome() == null) {
            throw new RequiredFieldMissingException("O campo Nome é obrigatório");
        }
    }

    private void existsIsAdmin(UsuarioDTO usuarioDTO) {
        if (usuarioDTO.getIsAdmin() == null) {
            throw new RequiredFieldMissingException("O campo IsAdmin é obrigatório");
        }
    }

    private void existsEmail(UsuarioDTO usuarioDTO) {
        if (usuarioDTO.getEmail() == null) {
            throw new RequiredFieldMissingException("O campo email é obrigatório");
        }
    }

    private void existsLogin(UsuarioDTO usuarioDTO) {
        if (usuarioDTO.getLogin() == null) {
            throw new RequiredFieldMissingException("O campo Login é obrigatório");
        }
    }

    private void existsDtNascimento(UsuarioDTO usuario) {
        if (usuario.getDtNascimento() == null) {
            throw new RequiredFieldMissingException("O campo dtNascimento é obrigatório");
        }
    }

    private void verificationAge(LocalDate dtNascimento) {
        Period idade = Period.between(dtNascimento, LocalDate.now());

        if (idade.getYears() < 18) {
            throw new UserIsUnderAgeException("O usuário deve possuir mais de 18 anos.");
        }
    }

    private LocalDate verificationDate(String dtNascimentoStr) throws DateTimeParseException {
        if (dtNascimentoStr == null) {
            return null;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dtNascimento = LocalDate.parse(dtNascimentoStr, dateTimeFormatter);

        return dtNascimento;
    }

    private void isUniqueLoginUser(UsuarioDTO usuario) {
        Optional<Usuario> optionalUser = usuarioRepository.findUsuarioByLogin(usuario.getLogin());
        if (optionalUser.isPresent()) {
            throw new EntityExistsException("Já existe um usuário cadastrado com este login: " + usuario.getLogin());
        }
    }

    private void isUniqueEmailUser(UsuarioDTO usuario) {
        Optional<Usuario> optionalUser = usuarioRepository.findUsuarioByEmail(usuario.getNome());
        if (optionalUser.isPresent()) {
            throw new EntityExistsException("Já existe este email de usuário cadastrado: " + usuario.getEmail());
        }
    }

    @Transactional
    public void delete(Long id){
        Usuario usuarioEntity = usuarioRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User with id " + id + " was not found."));

        usuarioRepository.delete(usuarioEntity);
    }

    @Transactional
    public Usuario findById(Long id){
        return usuarioRepository.findUsuarioById(id).orElseThrow(
                () -> new EntityNotFoundException("User with id " + id + " was not found." )
        );
    }

    public Page<Usuario> listarTodosOsUsuarios
            (String nome, String login, Integer totalDePaginas) {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("nome"));
        List<Usuario> usuarios =  usuarioRepository.findAll(
                Specification.where(
                        SpecificationsUsuario.nome(nome).and(
                                SpecificationsUsuario.login(login)
                        )
                )
        );
        return new PageImpl(usuarios, pageable, calculateTotal(Long.valueOf(usuarios.size()), pageable));
    }

    public long calculateTotal(Long listSize, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        long pageOffset = pageable.getOffset();
        return pageOffset + listSize + (listSize == pageSize ? pageSize : 0);
    }

    public boolean usuarioEhAdmin(String auth) {
        String token = auth.substring(7);
        Long idUsuario = tokenService.getUsuarioPorId(token);
        Usuario loggedUser = usuarioRepository.findById(idUsuario).orElseThrow(
                () -> new IllegalArgumentException("Usuário não encontrado")
        );

        if (!loggedUser.getIsAdmin()) {
            return false;
        }
        return true;
    }
}

